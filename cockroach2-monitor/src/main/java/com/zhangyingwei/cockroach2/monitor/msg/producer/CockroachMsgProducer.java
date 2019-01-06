package com.zhangyingwei.cockroach2.monitor.msg.producer;

import com.zhangyingwei.cockroach2.common.Constants;
import com.zhangyingwei.cockroach2.monitor.msg.Msg;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class CockroachMsgProducer implements ICMsgProducer {
    private Queue<Msg> queue;

    public CockroachMsgProducer(Queue<Msg> queue) {
        this.queue = queue;
    }

    @Override
    public void produce(Msg msg) {
        queue.add(msg);
    }
}
