package com.zhangyingwei.cockroach2.samples.monitor;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.CockroachContext;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.core.store.HtmlTitleStore;
import com.zhangyingwei.cockroach2.monitor.msg.consumer.CockroachMonitorConsumer;
import com.zhangyingwei.cockroach2.monitor.msg.consumer.DefaultCockroachConsumer;

/**
 * @author zhangyw
 * @date: 2019/1/15
 * @desc:
 */
public class DefaultCockroachConsumerTest {
    public static void main(String[] args) {
        CockroachConfig config = new CockroachConfig().store(HtmlTitleStore.class).addLogConsumer(DefaultCockroachConsumer.class).addLogConsumer(CockroachMonitorConsumer.class);
        CockroachContext context = new CockroachContext(config);
        QueueHandler handler = new QueueHandler.Builder().build();
        for (int i = 0; i < 100; i++) {
            handler.add(new Task("http://zhangyingwei.com"));
        }
        context.start(handler);
    }
}
