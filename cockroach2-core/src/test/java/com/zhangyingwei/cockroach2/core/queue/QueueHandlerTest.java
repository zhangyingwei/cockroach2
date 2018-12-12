package com.zhangyingwei.cockroach2.core.queue;


import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.common.enmus.RequestType;
import lombok.extern.slf4j.Slf4j;
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
        QueueHandler handler = QueueHandler.initWithDefaultQueue(10);
        for (int i = 0; i < 100; i++) {
            handler.add(new Task("url").doWith(RequestType.POST).group("group".concat(i+"")).retry(100));
        }
        for (int i = 0; i < 101; i++) {
            handler.get(false);
        }
    }
}