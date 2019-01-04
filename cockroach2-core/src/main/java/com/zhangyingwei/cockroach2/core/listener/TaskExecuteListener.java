package com.zhangyingwei.cockroach2.core.listener;

import com.zhangyingwei.cockroach2.common.Task;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskExecuteListener implements ICListener<Task> {

    private String name;

    public void start( String name,Class clientClass,Class proxyClass,Class storeClass,int threadSleep ) {
        this.name = name;
    }

    public void before(final Task task) {
    }

    public void after(final Task task) {
    }

    public void execute(final Task task) {
    }

    public void store(final Task task) {
    }

    public void success(final Task task) {
    }

    public void failed(final Task task) {
    }

    public void stop(final String name) {
    }
}