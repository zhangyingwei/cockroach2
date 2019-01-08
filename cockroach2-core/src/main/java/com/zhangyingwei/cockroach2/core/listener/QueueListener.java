package com.zhangyingwei.cockroach2.core.listener;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.monitor.msg.LogMsgHandler;
import com.zhangyingwei.cockroach2.monitor.msg.Msg;
import com.zhangyingwei.cockroach2.queue.ICQueue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueueListener implements ICListener<Task> {
//    private Long count = 0L;

    public void get(final Task task) {
    }

    public void add(final Task task) {
//        System.out.println("queue add count:" + ++count);
    }

    public void create(Class<? extends ICQueue> clazz) {
    }
}
