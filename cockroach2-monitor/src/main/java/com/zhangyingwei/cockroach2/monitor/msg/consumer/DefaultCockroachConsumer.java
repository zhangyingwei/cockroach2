package com.zhangyingwei.cockroach2.monitor.msg.consumer;

import com.zhangyingwei.cockroach2.monitor.msg.Msg;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class DefaultCockroachConsumer implements ICMsgConsumer {
    @Override
    public void consusmer(Msg msg) {
        log.debug("msg from {}-{},in {}, content: {}",msg.getName(),msg.getGroup(),msg.getTimestamp(),msg.getMsgMap());
    }

    @Override
    public boolean acceptGroup(Msg.Group group) {
        return true;
    }

    @Override
    public String getGroup() {
        return "defaultconsumer";
    }
}