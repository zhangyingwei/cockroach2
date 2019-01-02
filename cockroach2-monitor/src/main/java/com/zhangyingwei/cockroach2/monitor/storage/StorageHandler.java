package com.zhangyingwei.cockroach2.monitor.storage;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.monitor.model.ExecutorModel;
import com.zhangyingwei.cockroach2.monitor.model.TaskModel;

import java.util.List;

public class StorageHandler {
    private static StorageClient storageClient = new StorageClient();

    public synchronized static void addExecutor(
            String name,
            Long startTimestamp,
            Class clientClass,
            Class proxyClass,
            Class storeClass,
            int threadSleep
    ) {
        ExecutorModel executorModel = new ExecutorModel();
        executorModel.setName(name);
        executorModel.setStartTimestamp(startTimestamp);
        executorModel.setProxyClass(proxyClass);
        executorModel.setStoreClass(storeClass);
        executorModel.setThreadSleep(threadSleep);
        executorModel.setClientClass(clientClass);
        executorModel.setSuccessNum(0L);
        executorModel.setFailedNum(0L);
        storageClient.addExecutor(executorModel);
    }

    public synchronized static List<ExecutorModel> listAllExecutors() {
        return storageClient.listAllExecutors();
    }

    public synchronized static ExecutorModel findExecutorByName(String name) {
        return storageClient.findExecutorByName(name);
    }

    public synchronized static ExecutorModel deleteExecutorByName(String name) {
        return storageClient.deleteExecutorByName(name);
    }

    public synchronized static void addTask(Task task) {
        TaskModel taskModel = new TaskModel(task);
        taskModel.setStartTimestamp(System.currentTimeMillis());
        storageClient.addTask(taskModel);
    }

    public synchronized static List<TaskModel> listAllTasks() {
        return storageClient.listAllTasks();
    }

    public synchronized static TaskModel findTaskById(Long id) {
        return storageClient.findTaskById(id);
    }

    public synchronized static TaskModel deleteTaskById(Long id) {
        return storageClient.deleteTaskById(id);
    }

    public synchronized static void updateTask(Task task) {
        TaskModel taskModel = storageClient.findTaskById(task.getId());
        TaskModel newTaskModel = new TaskModel(task);
        newTaskModel.setStartTimestamp(taskModel.getStartTimestamp());
        storageClient.addTask(newTaskModel);
    }

    public static void print() {
        storageClient.print();
    }
}