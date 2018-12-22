package com.zhangyingwei.cockroach2.core.http;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.CockroachContext;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.core.store.HtmlTitleStore;

public class CockroachHeaderGeneratosTest {
    public static void main(String[] args) {
        QueueHandler queue = QueueHandler.initWithDefaultQueue().withBlock(false);
        queue.add(new Task("http://zhangyingwei.com"));
        new CockroachContext(
                new CockroachConfig().appName("header generator test").numThread(1).store(HtmlTitleStore.class)
        ).start(queue);
    }
}