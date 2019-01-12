package com.zhangyingwei.cockroach2.monitor.http.server;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class CockroachHttpServerTest {

    @Test
    public void start() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        new CockroachHttpServer().start();
        latch.await();
    }
}