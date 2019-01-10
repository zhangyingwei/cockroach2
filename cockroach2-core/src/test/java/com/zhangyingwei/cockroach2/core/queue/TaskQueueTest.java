package com.zhangyingwei.cockroach2.core.queue;

import com.zhangyingwei.cockroach2.common.Task;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TaskQueueTest {

    @Test
    public void add() throws InterruptedException {
        TaskQueue queue = new TaskQueue(10);
        for (int i = 0; i < 20; i++) {
            queue.add(this.createTask(i));
        }
        while (!queue.isEmpty()) {
            queue.get();
//            queue.add(this.createTask(100));
        }
    }

    @Test
    public void add2(){
        TaskQueue queue = new TaskQueue(10);
        for (int i = 0; i < 20; i++) {
            queue.add(this.createTask(i));
        }
        log.info("queue size is: {}", queue.size());
    }

    public static void main(String[] args) {
        QueueHandler queueHandler = new QueueHandler.Builder(10).limit(10L).withBlock(true).build();
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Task task = queueHandler.get();
                System.out.println("get task: " + task);
            }
        }).start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 100; i++) {
                queueHandler.add(createTask(i));
            }
        }).start();
        log.info("queue size is: {}",queueHandler.size());
    }

    private static Task createTask(int i) {
        Task task = new Task("url");
        task.setPriority(i);
        return task;
    }
}