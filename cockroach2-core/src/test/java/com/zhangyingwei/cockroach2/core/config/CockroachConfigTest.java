package com.zhangyingwei.cockroach2.core.config;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.CockroachContext;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.core.store.HtmlTitleStore;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @author zhangyw
 * @date: 2019/1/9
 * @desc:
 */
public class CockroachConfigTest {

    @Test
    public void testPropertiesConfig() throws ClassNotFoundException {
        Properties properties = new Properties();
        properties.put("app.name", "cockroach-confit-test");
        properties.put("app.thread.sleep", 3000);
        properties.put("app.thread.num", 5);
        properties.put("app.store", HtmlTitleStore.class.getName());
        CockroachConfig config = new CockroachConfig(properties).runWithJunit();
        CockroachContext context = new CockroachContext(config);
        QueueHandler queue = QueueHandler.initWithDefaultQueue();
        queue.add(new Task("https://baidu.com"));
        queue.add(new Task("https://baidu.com"));
        context.start(queue);
    }
}