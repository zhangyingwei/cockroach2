package com.zhangyingwei.cockroach2.monitor.storage;

import com.zhangyingwei.cockroach2.monitor.model.ExecutorModel;
import com.zhangyingwei.cockroach2.monitor.model.QueueModel;
import com.zhangyingwei.cockroach2.monitor.model.TaskModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class StorageClient {
    private Map<String,ExecutorModel> executorModels;
    private QueueModel queueModel;
    private Map<Long,TaskModel> taskModels;

    public StorageClient() {
        this.executorModels = new ConcurrentHashMap<>();
        this.queueModel = new QueueModel();
        this.taskModels = new ConcurrentHashMap<>();
    }

    /**
     * 添加一个 executor
     * @param executorModel
     */
    public void addExecutor(ExecutorModel executorModel) {
        executorModels.put(executorModel.getName(), executorModel);
    }

    /**
     * 列出所有 executor
     * @return
     */
    public List<ExecutorModel> listAllExecutors() {
        return new ArrayList<>(executorModels.values()).stream()
                .parallel()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * 根据 name 查询 executor
     * @param name
     * @return
     */
    public ExecutorModel findExecutorByName(String name) {
        return executorModels.get(name);
    }

    /**
     * 根据 name 删除一个 executor
     * @param name
     * @return
     */
    public ExecutorModel deleteExecutorByName(String name) {
        return this.executorModels.remove(name);
    }

    /**
     * 添加一个 task
     * @param taskModel
     */
    public void addTask(TaskModel taskModel) {
        this.taskModels.put(taskModel.getId(), taskModel);
    }

    /**
     * 列出所有task
     * @return
     */
    public List<TaskModel> listAllTasks() {
        return new ArrayList<>(this.taskModels.values()).stream()
                .parallel()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * 根据 id 查找 task
     * @param id
     * @return
     */
    public TaskModel findTaskById(Long id) {
        return this.taskModels.get(id);
    }

    /**
     * 根据 id 删除 task
     * @param id
     * @return
     */
    public TaskModel deleteTaskById(Long id) {
        return this.taskModels.remove(id);
    }

    /**
     * 获取 queue 信息
     * @return
     */
    public QueueModel getQueueModel() {
        return this.queueModel;
    }

    public void print() {
        System.out.println(this.executorModels);
        System.out.println(this.queueModel);
        System.out.println(this.taskModels);
    }
}
