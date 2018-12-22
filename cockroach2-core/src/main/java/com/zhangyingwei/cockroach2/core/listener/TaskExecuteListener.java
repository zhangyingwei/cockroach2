package com.zhangyingwei.cockroach2.core.listener;

import com.zhangyingwei.cockroach2.common.Task;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskExecuteListener implements ICListener<Task> {
    //task start time
    private Long startTime = 0L;

    public void before(Task task) {
        startTime = System.currentTimeMillis();
    }

    public void after(Task task) {
        Long endTime = System.currentTimeMillis();
        log.info("task statu: {}",task.getStatu());
    }
}