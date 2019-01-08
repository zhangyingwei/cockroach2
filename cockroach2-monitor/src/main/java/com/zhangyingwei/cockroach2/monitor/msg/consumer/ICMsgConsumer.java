package com.zhangyingwei.cockroach2.monitor.msg.consumer;

import com.zhangyingwei.cockroach2.monitor.msg.Msg;

public interface ICMsgConsumer {
    void consusmer(Msg msg);
    boolean acceptGroup(Msg.Group group);
    String getGroup();
}