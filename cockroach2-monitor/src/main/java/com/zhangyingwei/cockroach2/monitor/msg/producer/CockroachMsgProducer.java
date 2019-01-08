package com.zhangyingwei.cockroach2.monitor.msg.producer;

import com.zhangyingwei.cockroach2.common.Constants;
import com.zhangyingwei.cockroach2.monitor.msg.Msg;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Slf4j
public class CockroachMsgProducer implements ICMsgProducer {
    private Map<String, BlockingQueue<Msg>> queueMap;

    public CockroachMsgProducer(Map<String, BlockingQueue<Msg>> queueMap) {
        this.queueMap = queueMap;
    }

    @Override
    public void produce(Msg msg) {
        try {
            for (BlockingQueue<Msg> queue : this.queueMap.values()) {
                queue.put(msg);
            }
        } catch (InterruptedException e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
