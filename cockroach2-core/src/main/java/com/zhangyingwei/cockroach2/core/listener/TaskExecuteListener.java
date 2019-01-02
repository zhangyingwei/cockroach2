package com.zhangyingwei.cockroach2.core.listener;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.monitor.storage.StorageHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskExecuteListener implements ICListener<Task> {

    public void start( String name,Class clientClass,Class proxyClass,Class storeClass,int threadSleep ) {
        StorageHandler.addExecutor(
                name,
                System.currentTimeMillis(),
                clientClass,
                proxyClass,
                storeClass,
                threadSleep
        );
    }

    public void before(final Task task) {
        StorageHandler.updateTask(task);
    }

    public void after(final Task task) {
        StorageHandler.updateTask(task);
    }

    public void execute(Task task) {
        StorageHandler.updateTask(task);
    }

    public void store(Task task) {
        StorageHandler.updateTask(task);
    }

    public void success(Task task) {
        StorageHandler.deleteTaskById(task.getId());
    }

    public void failed(Task task) {
        StorageHandler.deleteTaskById(task.getId());
    }

    public void stop(String name) {
        StorageHandler.deleteExecutorByName(name);
    }
}