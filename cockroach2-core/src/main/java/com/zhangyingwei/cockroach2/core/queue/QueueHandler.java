package com.zhangyingwei.cockroach2.core.queue;

import com.zhangyingwei.cockroach2.common.Constants;
import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.queue.filter.ICQueueFilter;
import com.zhangyingwei.cockroach2.queue.ICQueue;
import lombok.extern.slf4j.Slf4j;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */

@Slf4j
public class QueueHandler implements ICQueue {
    private ICQueue queue;
    private Set<ICQueueFilter> filters = new HashSet<ICQueueFilter>();

    private QueueHandler(ICQueue queue) {
        this.queue = queue;
    }

    private QueueHandler(int calacify) {
        this(new TaskQueue(calacify));
    }

    public static QueueHandler initQueue(ICQueue queue) {
        return new QueueHandler(queue);
    }

    public static QueueHandler initWithDefaultQueue(int calacify) {
        return new QueueHandler(calacify);
    }

    public static QueueHandler initWithDefaultQueue() {
        return initWithDefaultQueue(Constants.QUEUE_SIZE);
    }

    public QueueHandler withFilter(ICQueueFilter filter) {
        this.filters.add(filter);
        return this;
    }

    @Override
    public Task get(Boolean withBlock) {
        return queue.get(withBlock);
    }

    @Override
    public synchronized void add(Task task) {
        Boolean accept = true;
        for (ICQueueFilter filter : filters) {
            if (!filter.accept(task)) {
                accept = false;
                log.info("task is not accept by {}", filter.getClass());
                break;
            }
        }
        if (accept) {
            this.queue.add(task);
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
