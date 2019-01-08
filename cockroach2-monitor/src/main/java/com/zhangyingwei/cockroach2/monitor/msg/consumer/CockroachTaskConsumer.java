package com.zhangyingwei.cockroach2.monitor.msg.consumer;

import com.zhangyingwei.cockroach2.monitor.msg.Msg;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class CockroachTaskConsumer implements ICMsgConsumer {
    private Long taskCount = 0L;

    @Override
    public void consusmer(Msg msg) {
        if ("before".equals(msg.getMsgOf(Msg.Keys.TASK_ACTION))) {
            taskCount++;
            System.out.println(taskCount);
        }
        if (msg.getGroup().equals(Msg.Group.APPLICATION)) {
            log.debug("application action : {}",msg.getMsgOf(Msg.Keys.APPLICATION_ACTION));
        }
        if ("stop".equals(msg.getMsgOf(Msg.Keys.APPLICATION_ACTION))) {
            log.info("task count is :{}", taskCount);
        }
    }

    @Override
    public boolean acceptGroup(Msg.Group group) {
        return Msg.Group.TASK.equals(group) || Msg.Group.APPLICATION.equals(group);
    }

    @Override
    public String getGroup() {
        return "taskconsumer";
    }
}