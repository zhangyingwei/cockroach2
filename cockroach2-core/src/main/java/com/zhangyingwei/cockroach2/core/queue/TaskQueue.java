package com.zhangyingwei.cockroach2.core.queue;

import com.zhangyingwei.cockroach2.common.Constants;
import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.queue.ICQueue;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */

@Slf4j
public class TaskQueue implements ICQueue {
    private BlockingQueue<Task> queue;

    public TaskQueue() {
        this(Constants.QUEUE_SIZE);
    }

    public TaskQueue(int calacity) {
        this.queue = new PriorityBlockingQueue<Task>(calacity, new Comparator<Task>() {
            @Override
            public int compare(Task left, Task right) {
                return left.compareTo(right);
            }
        });
        log.info("create queue with calacity: {}",calacity);
    }

    @Override
    public Task get() {
        Task task = this.queue.poll();
        log.debug("take task: {}", task);
        return task;
    }



    @Override
    public void add(Task task) {
        try {
            this.queue.put(task);
            log.debug("put task: {}", task);
        } catch (InterruptedException e) {
            log.info("add task faild:{} - {}",task,e.getLocalizedMessage());
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

    //TODO
    class TaskPriorityBlockQueue extends PriorityBlockingQueue<Task> {
        @Override
        public void put(Task task) {
            super.put(task);
        }
    }
}
