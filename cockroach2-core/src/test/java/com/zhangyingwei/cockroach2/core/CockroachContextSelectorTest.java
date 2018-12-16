package com.zhangyingwei.cockroach2.core;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.core.store.HtmlTitleStore;
import com.zhangyingwei.cockroach2.core.store.PrintStore;
import com.zhangyingwei.cockroach2.core.store.StoreTest;

public class CockroachContextSelectorTest {

//    @Test
    public static void main(String[] args) {
        CockroachConfig config = new CockroachConfig().appName("name")
                .threadSeep(1000)
                .store(StoreTest.class);
        CockroachContext context = new CockroachContext(config);
        QueueHandler queueHandler = QueueHandler.initWithDefaultQueue();
        queueHandler.add(new Task("http://blog.zhangyingwei.com"));
        context.start(queueHandler);
    }
}