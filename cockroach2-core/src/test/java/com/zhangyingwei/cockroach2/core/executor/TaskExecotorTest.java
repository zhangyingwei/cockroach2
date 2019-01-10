package com.zhangyingwei.cockroach2.core.executor;

import com.zhangyingwei.cockroach2.common.async.AsyncManager;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.*;
import java.util.stream.Stream;

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

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
//        new TaskExecotorTest().stop();
//        new TaskExecotorTest().execute();
        new TaskExecotorTest().feature();
    }

    public void execute() {
        Stream.generate(Math::random).limit(100000).parallel().forEach(num -> {
            System.out.println(num+" : "+System.currentTimeMillis());
        });
        System.out.println("after stream");
    }

    public void feature() throws InterruptedException {
        AsyncManager manager = new AsyncManager();
        manager.doVoidMethodAsync(() -> {
            System.out.println("");
        });
        System.out.println("after method");
        manager.shutdown();
    }
}