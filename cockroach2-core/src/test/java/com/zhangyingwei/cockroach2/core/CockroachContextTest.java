package com.zhangyingwei.cockroach2.core;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.core.store.HtmlTitleStore;
import org.junit.Test;

public class CockroachContextTest {

//    @Test
    public static void main(String[] args) {
        CockroachConfig config = new CockroachConfig().appName("name")
                .threadSeep(100)
                .store(HtmlTitleStore.class);
        CockroachContext context = new CockroachContext(config);
        QueueHandler queueHandler = new QueueHandler.Builder().withBlock(false).build();
        for (int i = 0; i < 10; i++) {
            queueHandler.add(new Task("http://zhangyingwei.com"));
        }
        context.start(queueHandler);
        System.out.println("@@@@@@@@@@@@@@@@@@@");
    }

    @Test
    public void test() {
        CockroachConfig config = new CockroachConfig().appName("name")
                .threadSeep(10)
                .numThread(2)
                .store(HtmlTitleStore.class).runWithJunit();
        CockroachContext context = new CockroachContext(config);
        QueueHandler queueHandler = new QueueHandler.Builder().withBlock(false).build();
        for (int i = 0; i < 10; i++) {
            queueHandler.add(new Task("http://zhangyingwei.com"));
        }
        context.start(queueHandler);
    }

    @Test
    public void start() {
        //TODO
    }
}