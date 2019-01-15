package com.zhangyingwei.cockroach2.core.listener.impls.application;

import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.listener.ICListener;
import com.zhangyingwei.cockroach2.monitor.msg.LogMsgHandler;
import com.zhangyingwei.cockroach2.monitor.msg.Msg;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyw
 * @date: 2019/1/15
 * @desc:
 */
@Slf4j
public class ApplicationStartListener implements ICListener<CockroachConfig> {
    private final CockroachConfig config;
    private final LogMsgHandler logMsgHandler;

    public ApplicationStartListener(CockroachConfig config) {
        this.config = config;
        this.logMsgHandler = this.config.getLogMsgHandler();
    }

    @Override
    public void action(CockroachConfig tmpconfig) {
        log.info("executors start!");
        Msg msg = new Msg("application", Msg.Group.APPLICATION);
        msg.addMsg(Msg.Keys.APPLICATION_ACTION, "start");
        msg.addMsg(Msg.Keys.APPLICATION_NAME, this.config.getAppName());
        msg.addMsg(Msg.Keys.APPLICATION_NUM_THREAD, this.config.getNumThread());
        this.logMsgHandler.produce(msg);
    }

    @Override
    public ListenerType type() {
        return ListenerType.APPLICATION_START;
    }
}