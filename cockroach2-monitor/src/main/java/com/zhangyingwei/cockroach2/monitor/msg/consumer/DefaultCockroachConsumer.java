package com.zhangyingwei.cockroach2.monitor.msg.consumer;

import com.zhangyingwei.cockroach2.monitor.msg.Msg;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultCockroachConsumer implements ICMsgConsumer {
    @Override
    public void consusmer(Msg msg) {
        log.debug("msg from {},in {}, content: {}",msg.getName(),msg.getTimestamp(),msg.getMsgMap());
    }
}
