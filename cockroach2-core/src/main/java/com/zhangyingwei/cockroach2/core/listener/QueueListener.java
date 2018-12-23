package com.zhangyingwei.cockroach2.core.listener;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.monitor.storage.StorageClient;
import com.zhangyingwei.cockroach2.monitor.storage.StorageHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueueListener implements ICListener<Task> {

    public void get(final Task task) {
        StorageHandler.updateTask(task);
    }

    public void add(final Task task) {
        StorageHandler.addTask(task);
    }
}
