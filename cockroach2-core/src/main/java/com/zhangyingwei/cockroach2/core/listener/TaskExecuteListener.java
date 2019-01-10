package com.zhangyingwei.cockroach2.core.listener;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.executor.TaskExecutor;
import com.zhangyingwei.cockroach2.monitor.msg.LogMsgHandler;
import com.zhangyingwei.cockroach2.monitor.msg.Msg;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskExecuteListener implements ICListener<Task> {
    private final CockroachConfig config;

    public TaskExecuteListener(CockroachConfig config) {
        this.config = config;
    }

    public void start(String name) {
        Msg msg = new Msg(name, Msg.Group.EXECUTOR);
        msg.addMsg(Msg.Keys.EXECUTOR_ACTION, "start");
        msg.addMsg(Msg.Keys.EXECUTOR_TIMESTAMP, System.currentTimeMillis());
        config.getLogMsgHandler().produce(msg);
    }

    public void before(final Task task) {
        Msg msg = new Msg("task-" + task.getId(), Msg.Group.TASK);
        msg.addMsg(Msg.Keys.TASK_ACTION, "before");
        msg.addMsg(Msg.Keys.TASK_STATE, task.getStatu());
        msg.addMsg(Msg.Keys.TASK_URL, task.getUrl());
        msg.addMsg(Msg.Keys.TASK_GROUP, task.getGroup());
        msg.addMsg(Msg.Keys.TASK_REQ_TYPE, task.getRequestType());
        msg.addMsg(Msg.Keys.TASK_REQ_PARAMS, task.getParams());
        config.getLogMsgHandler().produce(msg);
    }

    public void after(final Task task) {
        Msg msg = new Msg("task-" + task.getId(), Msg.Group.TASK);
        msg.addMsg(Msg.Keys.TASK_ACTION, "after");
        msg.addMsg(Msg.Keys.TASK_STATE, task.getStatu());
        msg.addMsg(Msg.Keys.TASK_URL, task.getUrl());
        msg.addMsg(Msg.Keys.TASK_GROUP, task.getGroup());
        msg.addMsg(Msg.Keys.TASK_REQ_TYPE, task.getRequestType());
        msg.addMsg(Msg.Keys.TASK_REQ_PARAMS, task.getParams());
        config.getLogMsgHandler().produce(msg);
    }

    public void execute(final Task task) {
        Msg msg = new Msg("task-" + task.getId(), Msg.Group.TASK);
        msg.addMsg(Msg.Keys.TASK_ACTION, "execute");
        msg.addMsg(Msg.Keys.TASK_STATE, task.getStatu());
        msg.addMsg(Msg.Keys.TASK_URL, task.getUrl());
        msg.addMsg(Msg.Keys.TASK_GROUP, task.getGroup());
        msg.addMsg(Msg.Keys.TASK_REQ_TYPE, task.getRequestType());
        msg.addMsg(Msg.Keys.TASK_REQ_PARAMS, task.getParams());
        config.getLogMsgHandler().produce(msg);
    }

    public void store(final Task task) {
        Msg msg = new Msg("task-" + task.getId(), Msg.Group.TASK);
        msg.addMsg(Msg.Keys.TASK_ACTION, "store");
        msg.addMsg(Msg.Keys.TASK_STATE, task.getStatu());
        msg.addMsg(Msg.Keys.TASK_URL, task.getUrl());
        msg.addMsg(Msg.Keys.TASK_GROUP, task.getGroup());
        msg.addMsg(Msg.Keys.TASK_REQ_TYPE, task.getRequestType());
        msg.addMsg(Msg.Keys.TASK_REQ_PARAMS, task.getParams());
        config.getLogMsgHandler().produce(msg);
    }

    public void success(final Task task) {
        Msg msg = new Msg("task-" + task.getId(), Msg.Group.TASK);
        msg.addMsg(Msg.Keys.TASK_ACTION, "success");
        msg.addMsg(Msg.Keys.TASK_STATE, task.getStatu());
        msg.addMsg(Msg.Keys.TASK_URL, task.getUrl());
        msg.addMsg(Msg.Keys.TASK_GROUP, task.getGroup());
        msg.addMsg(Msg.Keys.TASK_REQ_TYPE, task.getRequestType());
        msg.addMsg(Msg.Keys.TASK_REQ_PARAMS, task.getParams());
        config.getLogMsgHandler().produce(msg);
    }

    public void failed(final Task task) {
        Msg msg = new Msg("task-" + task.getId(), Msg.Group.TASK);
        msg.addMsg(Msg.Keys.TASK_ACTION, "failed");
        msg.addMsg(Msg.Keys.TASK_STATE, task.getStatu());
        msg.addMsg(Msg.Keys.TASK_URL, task.getUrl());
        msg.addMsg(Msg.Keys.TASK_GROUP, task.getGroup());
        msg.addMsg(Msg.Keys.TASK_REQ_TYPE, task.getRequestType());
        msg.addMsg(Msg.Keys.TASK_REQ_PARAMS, task.getParams());
        config.getLogMsgHandler().produce(msg);
    }

    public void stop(final String name) {
        Msg msg = new Msg(name, Msg.Group.EXECUTOR);
        msg.addMsg(Msg.Keys.EXECUTOR_ACTION, "stop");
        msg.addMsg(Msg.Keys.EXECUTOR_TIMESTAMP, System.currentTimeMillis());
        config.getLogMsgHandler().produce(msg);
    }
}