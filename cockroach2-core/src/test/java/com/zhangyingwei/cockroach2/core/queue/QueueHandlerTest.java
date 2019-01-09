package com.zhangyingwei.cockroach2.core.queue;


import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.common.enmus.RequestType;
import lombok.extern.slf4j.Slf4j;
import org.fusesource.jansi.Ansi;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
@Slf4j
public class QueueHandlerTest {
    @Test
    public void test1() {
        QueueHandler handler = QueueHandler.initWithDefaultQueue(10);
        for (int i = 0; i < 100; i++) {
            handler.add(new Task("url").doWith(RequestType.POST).group("group".concat(i+"")).tryNum(100));
        }
        for (int i = 0; i < 101; i++) {
            handler.get();
        }
    }

    @Test
    public void withFilter() {
        QueueHandler queue = QueueHandler.initWithDefaultQueue();
        queue.withFilter((task) -> {
            return task.getUrl() != null;
        }).withFilter((task) -> {
            return !task.getUrl().contains("baidu");
        });
        queue.add(new Task(null));
        queue.add(new Task("baidu"));
        queue.add(new Task("http://baidu.com"));
        queue.add(new Task("http://taobao.com"));
    }

    @Test
    public void queueNoFilterTest() {
        QueueHandler queue = QueueHandler.initWithDefaultQueue();
        queue.add(new Task(null));
        queue.add(new Task("baidu"));
        queue.add(new Task("http://baidu.com"));
        queue.add(new Task("http://taobao.com"));
    }

    @Test
    public void printColorTest() {
//        System.out.println(Ansi.ansi().bg(Ansi.Color.RED).a("hello cockroach"));
    }
}