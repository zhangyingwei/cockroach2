package com.zhangyingwei.cockroach2.core.listener.impls.executor;

import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.listener.ICListener;
import com.zhangyingwei.cockroach2.monitor.msg.Msg;

/**
 * @author zhangyw
 * @date: 2019/1/15
 * @desc:
 */
public class ExecutorStartListener implements ICListener<String> {
    private final CockroachConfig config;

    public ExecutorStartListener(CockroachConfig config) {
        this.config = config;
    }

    @Override
    public void action(String name) {
        Msg msg = new Msg(name, Msg.Group.EXECUTOR);
        msg.addMsg(Msg.Keys.EXECUTOR_ACTION, "start");
        msg.addMsg(Msg.Keys.EXECUTOR_TIMESTAMP, System.currentTimeMillis());
        config.getLogMsgHandler().produce(msg);
    }

    @Override
    public ListenerType type() {
        return ListenerType.EXECUTOR_START;
    }
}
