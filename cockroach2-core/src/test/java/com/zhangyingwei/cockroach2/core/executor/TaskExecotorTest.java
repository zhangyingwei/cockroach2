package com.zhangyingwei.cockroach2.core.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TaskExecotorTest {
    public void stop() {
        Thread thread1 = new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                    log.info(String.valueOf(System.currentTimeMillis()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                log.info("stop...");
                thread1.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        new TaskExecotorTest().stop();
    }
}