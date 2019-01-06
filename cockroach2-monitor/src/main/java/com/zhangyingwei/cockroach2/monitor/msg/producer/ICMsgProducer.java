package com.zhangyingwei.cockroach2.monitor.msg.producer;

import com.zhangyingwei.cockroach2.monitor.msg.Msg;

public interface ICMsgProducer {
    void produce(Msg msg);
}
