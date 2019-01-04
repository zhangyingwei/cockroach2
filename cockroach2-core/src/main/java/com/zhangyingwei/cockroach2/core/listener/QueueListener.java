package com.zhangyingwei.cockroach2.core.listener;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.queue.ICQueue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueueListener implements ICListener<Task> {

    public void get(final Task task) {
//        handler.taskUpdateStatu(task);
    }

    public void add(final Task task) {
    }

    public void create(Class<? extends ICQueue> clazz) {
    }
}
