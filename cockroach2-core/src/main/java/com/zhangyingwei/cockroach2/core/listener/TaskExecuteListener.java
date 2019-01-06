package com.zhangyingwei.cockroach2.core.listener;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.executor.TaskExecutor;
import com.zhangyingwei.cockroach2.monitor.msg.LogMsgHandler;
import com.zhangyingwei.cockroach2.monitor.msg.Msg;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskExecuteListener implements ICListener<Task> {
    private LogMsgHandler logMsgHandler;

    public TaskExecuteListener(LogMsgHandler logMsgHandler) {
        this.logMsgHandler = logMsgHandler;
    }

    public void start(String name) {

    }

    public void before(final Task task) {
        Msg msg = new Msg("task-" + task.getId());
        msg.addMsg("executor.task.before", task.getUrl());
        logMsgHandler.produce(msg);
    }

    public void after(final Task task) {
        Msg msg = new Msg("task-" + task.getId());
        msg.addMsg("executor.task.after", task.getUrl());
        logMsgHandler.produce(msg);
    }

    public void execute(final Task task) {
        Msg msg = new Msg("task-" + task.getId());
        msg.addMsg("executor.task.execute", task.getUrl());
        logMsgHandler.produce(msg);
    }

    public void store(final Task task) {
        Msg msg = new Msg("task-" + task.getId());
        msg.addMsg("executor.task.store", task.getUrl());
        logMsgHandler.produce(msg);
    }

    public void success(final Task task) {
        Msg msg = new Msg("task-" + task.getId());
        msg.addMsg("executor.task.success", task.getUrl());
        logMsgHandler.produce(msg);
    }

    public void failed(final Task task) {
        Msg msg = new Msg("task-" + task.getId());
        msg.addMsg("executor.task.failed", task.getUrl());
        logMsgHandler.produce(msg);
    }

    public void stop(final String name) {

    }
}