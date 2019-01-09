package com.zhangyingwei.cockroach2.core.queue;

import com.zhangyingwei.cockroach2.common.Constants;
import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.common.async.AsyncUtils;
import com.zhangyingwei.cockroach2.common.utils.LogUtils;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.listener.QueueListener;
import com.zhangyingwei.cockroach2.core.queue.filter.ICQueueFilter;
import com.zhangyingwei.cockroach2.queue.ICQueue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */

@Slf4j
public class QueueHandler implements ICQueue {
    private ICQueue queue;
    private Set<ICQueueFilter> filters = new HashSet<ICQueueFilter>();
    @Getter
    private Boolean withBlock = false;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private Long limitSize = -1L;
    private final QueueListener listener = new QueueListener();

    private QueueHandler(ICQueue queue) {
        this.queue = queue;
        this.listener.create(queue.getClass());
    }

    public static QueueHandler initWithQueue(ICQueue queue) {
        return new QueueHandler(queue);
    }

    public static QueueHandler initWithDefaultQueue(int calacify) {
        return new QueueHandler(new TaskQueue(calacify));
    }

    public static QueueHandler initWithDefaultQueue() {
        return initWithDefaultQueue(Constants.QUEUE_SIZE);
    }

    public QueueHandler withFilter(ICQueueFilter filter) {
        this.filters.add(filter);
        return this;
    }

    public QueueHandler limit(Long limit) {
        this.limitSize = limit;
        return this;
    }

    public QueueHandler withBlock(Boolean block) {
        this.withBlock = block;
        return this;
    }

    @Override
    public Task get() {
        try {
            this.lock.lock();
            Task task = queue.get();
            if (task != null) {
                task.statu(Task.Statu.START).tryOne();
            }
            this.condition.signalAll();
            if (this.withBlock) {
                while (task == null && this.queue.isEmpty()) {
                    log.debug("{}: queue get was blocked, size is: {}",LogUtils.getQueueTagColor("queue"),queue.size());
                    this.condition.await();
                    task = queue.get();
                }
            } else {
                /**
                 * Variable used in lambda expression should be final or effectively final
                 * 因为 task 在上边被修改过一次，所以不能直接被传到 lambda 表达式中，因此需要下边这行代码
                 */
                log.debug("{}: get task: {}",LogUtils.getQueueTagColor("queue"),task);
                Task finalTask = task;
                AsyncUtils.doVoidMethodAsync(() -> listener.get(finalTask));
                return task;
            }
            log.debug("{}: get task: {}",LogUtils.getQueueTagColor("queue"),task);
            Task finalTask1 = task;
            AsyncUtils.doVoidMethodAsync(() -> listener.get(finalTask1));
            return task;
        } catch (InterruptedException e) {
            log.error("{}: get task from queue error: {}",LogUtils.getQueueTagColor("queue"), e.getLocalizedMessage());
        } finally {
            this.lock.unlock();
        }
        return null;
    }

    @Override
    public void add(Task task) {
        try {
            this.lock.lock();
            Boolean accept = true;
            final List<ICQueueFilter> queueFilters = new ArrayList<ICQueueFilter>(filters);
            for (int i = queueFilters.size()-1; i >=0; i--) {
                if (!queueFilters.get(i).accept(task)) {
                    accept = false;
                    log.info("{}: task was not accept by {}", LogUtils.getQueueTagColor("queue"), queueFilters.get(i).getClass());
                    break;
                }
            }
            if (accept) {
                if (this.limitSize > 0) {
                    while (this.queue.size() >= this.limitSize) {
                        log.debug("{}: queue add was blocked, size is: {}", LogUtils.getQueueTagColor("queue"), queue.size());
                        this.condition.await();
                    }
                    this.queue.add(task);
                    log.debug("{}: add task: {}",LogUtils.getQueueTagColor("queue"),task);
                    AsyncUtils.doVoidMethodAsync(() -> listener.add(task));
                    this.condition.signalAll();
                } else {
                    this.queue.add(task);
                    log.debug("{}: add task: {}",LogUtils.getQueueTagColor("queue"),task);
                    AsyncUtils.doVoidMethodAsync(() -> listener.add(task));
                }
            }
        } catch (InterruptedException e) {
            log.error("{}: add task to queue error: {}", LogUtils.getQueueTagColor("queue"), e.getLocalizedMessage());
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public int size() {
        return this.queue.size();
    }

    @Override
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }
}
