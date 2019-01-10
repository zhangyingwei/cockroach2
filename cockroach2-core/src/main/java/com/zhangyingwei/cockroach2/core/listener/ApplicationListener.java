package com.zhangyingwei.cockroach2.core.listener;

import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.monitor.msg.LogMsgHandler;
import com.zhangyingwei.cockroach2.monitor.msg.Msg;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyw
 * @date: 2019/1/2
 * @desc:
 */

@Slf4j
public class ApplicationListener implements ICListener {
    private final CockroachConfig config;
    private LogMsgHandler logMsgHandler;

    public ApplicationListener(CockroachConfig config) {
        this.config = config;
        this.logMsgHandler = config.getLogMsgHandler();
    }

    public void onStart() {
        log.info("executors start!");
        Msg msg = new Msg("application", Msg.Group.APPLICATION);
        msg.addMsg(Msg.Keys.APPLICATION_ACTION, "start");
        msg.addMsg(Msg.Keys.APPLICATION_NAME, this.config.getAppName());
        msg.addMsg(Msg.Keys.APPLICATION_NUM_THREAD, this.config.getNumThread());
        this.logMsgHandler.produce(msg);
    }

    public void onStop() {
        log.info("executors done!");
        Msg msg = new Msg("application", Msg.Group.APPLICATION);
        msg.addMsg(Msg.Keys.APPLICATION_ACTION, "stop");
        msg.addMsg(Msg.Keys.APPLICATION_NAME, this.config.getAppName());
        this.logMsgHandler.produce(msg);
    }
}
