package com.zhangyingwei.cockroach2.core;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.core.store.HtmlTitleStore;
import org.junit.Test;

import static org.junit.Assert.*;

public class CockroachContextTest {

//    @Test
    public static void main(String[] args) {
        CockroachConfig config = new CockroachConfig().appName("name")
                .threadSeep(1000)
                .store(HtmlTitleStore.class);
        CockroachContext context = new CockroachContext(config);
        QueueHandler queueHandler = QueueHandler.initWithDefaultQueue();
        for (int i = 0; i < 100; i++) {
            queueHandler.add(new Task("http://zhangyingwei.com"));
        }
        context.start(queueHandler);
    }

    @Test
    public void test() {
        CockroachConfig config = new CockroachConfig().appName("name")
                .threadSeep(1000)
                .store(HtmlTitleStore.class);
        CockroachContext context = new CockroachContext(config);
        QueueHandler queueHandler = QueueHandler.initWithDefaultQueue().withBlock(true);
        for (int i = 0; i < 10; i++) {
            queueHandler.add(new Task("http://zhangyingwei.com"));
        }
        context.start(queueHandler);
    }
}