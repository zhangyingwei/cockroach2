package com.zhangyingwei.cockroach2.core.queue;


import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.common.enmus.RequestType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
@Slf4j
public class QueueHandlerTest {
    @Test
    public void test1() {
        QueueHandler handler = new QueueHandler.Builder(10).build();
        for (int i = 0; i < 100; i++) {
            handler.add(new Task("url").doWith(RequestType.POST).group("group".concat(i+"")).tryNum(100));
        }
        for (int i = 0; i < 101; i++) {
            handler.get();
        }
    }

    @Test
    public void withFilter() {
        QueueHandler queue = new QueueHandler.Builder().withFilter((task) -> {
            return task.getUrl() != null;
        }).withFilter((task) -> {
            return !task.getUrl().contains("baidu");
        }).build();
        queue.add(new Task(null));
        queue.add(new Task("baidu"));
        queue.add(new Task("http://baidu.com"));
        queue.add(new Task("http://taobao.com"));
        Assert.assertEquals(queue.size(), 1);
    }

    @Test
    public void queueNoFilterTest() {
        QueueHandler queue = new QueueHandler.Builder().build();
        queue.add(new Task(null));
        queue.add(new Task("baidu"));
        queue.add(new Task("http://baidu.com"));
        queue.add(new Task("http://taobao.com"));
        Assert.assertEquals(queue.size(), 4);
    }

    @Test
    public void printColorTest() {
//        System.out.println(Ansi.ansi().bg(Ansi.Color.RED).a("hello cockroach"));
    }

    @Test
    public void initWithQueue() {
        //TODO
    }

    @Test
    public void initWithDefaultQueue() {
        //TODO
    }

    @Test
    public void initWithDefaultQueue1() {
        //TODO
    }

    @Test
    public void withFilter1() {
        //TODO
    }

    @Test
    public void limit() {
        //TODO
    }

    @Test
    public void withBlock() {
        //TODO
    }

    @Test
    public void get() {
        //TODO
    }

    @Test
    public void add() {
        //TODO
    }

    @Test
    public void size() {
        //TODO
    }

    @Test
    public void isEmpty() {
        //TODO
    }

    @Test
    public void getWithBlock() {
        //TODO
    }
}