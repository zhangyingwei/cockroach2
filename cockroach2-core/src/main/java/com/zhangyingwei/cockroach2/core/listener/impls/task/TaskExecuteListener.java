package com.zhangyingwei.cockroach2.core.listener.impls.task;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.listener.ICListener;
import com.zhangyingwei.cockroach2.monitor.msg.Msg;

/**
 * @author zhangyw
 * @date: 2019/1/15
 * @desc:
 */
public class TaskExecuteListener implements ICListener<Task> {
    private final CockroachConfig config;

    public TaskExecuteListener(CockroachConfig config) {
        this.config = config;
    }

    @Override
    public void action(Task task) {
        Msg msg = new Msg("task-" + task.getId(), Msg.Group.TASK);
        msg.addMsg(Msg.Keys.TASK_ACTION, "execute");
        msg.addMsg(Msg.Keys.TASK_STATE, task.getStatu());
        msg.addMsg(Msg.Keys.TASK_URL, task.getUrl());
        msg.addMsg(Msg.Keys.TASK_GROUP, task.getGroup());
        msg.addMsg(Msg.Keys.TASK_REQ_TYPE, task.getRequestType());
        msg.addMsg(Msg.Keys.TASK_REQ_PARAMS, task.getParams());
        config.getLogMsgHandler().produce(msg);
    }

    @Override
    public ListenerType type() {
        return ListenerType.TASK_EXECUTE;
    }
}
