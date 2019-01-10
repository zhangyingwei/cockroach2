package com.zhangyingwei.cockroach2.core.queue;

import com.zhangyingwei.cockroach2.common.Constants;
import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.common.async.AsyncManager;
import com.zhangyingwei.cockroach2.common.utils.LogUtils;
import com.zhangyingwei.cockroach2.core.listener.QueueListener;
import com.zhangyingwei.cockroach2.core.queue.filter.ICQueueFilter;
import com.zhangyingwei.cockroach2.queue.ICQueue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */

@Slf4j
public class QueueHandler implements ICQueue {
    private ICQueue queue;
    private Queue<ICQueueFilter> filters;
    @Getter
    private Boolean withBlock;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private Long limitSize;

    private QueueHandler(Builder builder) {
        this.queue = builder.queue;
        this.filters = builder.filters;
        this.withBlock = builder.withBlock;
        this.limitSize = builder.limitSize;
    }

    public static class Builder {
        private ICQueue queue;
        private Queue<ICQueueFilter> filters = new ArrayDeque<ICQueueFilter>();
        private Boolean withBlock = false;
        private final ReentrantLock lock = new ReentrantLock();
        private final Condition condition = lock.newCondition();
        private Long limitSize = -1L;

        public Builder(ICQueue queue) {
            this.queue = queue;
        }

        public Builder(int calacify) {
            this.queue = new TaskQueue(calacify);
        }

        public Builder() {
            this(Constants.QUEUE_SIZE);
        }

        public Builder withFilter(ICQueueFilter filter) {
            this.filters.add(filter);
            return this;
        }

        public Builder limit(Long limit) {
            this.limitSize = limit;
            return this;
        }

        public Builder withBlock(Boolean block) {
            this.withBlock = block;
            return this;
        }

        public QueueHandler build() {
            return new QueueHandler(this);
        }
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
                return task;
            }
            log.debug("{}: get task: {}",LogUtils.getQueueTagColor("queue"),task);
            Task finalTask1 = task;
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
            Boolean accept = !this.filters.stream().anyMatch(filter -> {
                boolean itemAccept = filter.accept(task);
                if (!itemAccept) {
                    log.info("{}: {} was not accepted by {}", LogUtils.getQueueTagColor("queue"), task, filter.getClass());
                }
                return !itemAccept;
            });
            if (accept) {
                if (this.limitSize > 0) {
                    while (this.queue.size() >= this.limitSize) {
                        log.debug("{}: queue add was blocked, size is: {}", LogUtils.getQueueTagColor("queue"), queue.size());
                        this.condition.await();
                    }
                    this.queue.add(task);
                    log.debug("{}: add task: {}",LogUtils.getQueueTagColor("queue"),task);
                    this.condition.signalAll();
                } else {
                    this.queue.add(task);
                    log.debug("{}: add task: {}",LogUtils.getQueueTagColor("queue"),task);
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