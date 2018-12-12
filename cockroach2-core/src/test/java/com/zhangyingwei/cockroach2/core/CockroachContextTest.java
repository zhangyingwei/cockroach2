package com.zhangyingwei.cockroach2.core;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import org.junit.Test;

import static org.junit.Assert.*;

public class CockroachContextTest {

    @Test
    public void start() {
        CockroachConfig config = new CockroachConfig().appName("name")
                .autoClose(false);
        CockroachContext context = new CockroachContext(config);
        QueueHandler queueHandler = QueueHandler.initWithDefaultQueue();
        for (int i = 0; i < 100; i++) {
            queueHandler.add(new Task("http://zhangyingwei.com"));
        }
        context.start(queueHandler);
    }
}