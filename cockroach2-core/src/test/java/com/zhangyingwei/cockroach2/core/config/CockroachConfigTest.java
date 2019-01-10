package com.zhangyingwei.cockroach2.core.config;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.CockroachContext;
import com.zhangyingwei.cockroach2.core.generator.TestCookieGenerator;
import com.zhangyingwei.cockroach2.core.generator.TestHeaderGenerator;
import com.zhangyingwei.cockroach2.core.generator.TestProxyGenerator;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.core.store.HtmlTitleStore;
import com.zhangyingwei.cockroach2.http.okhttp.COkHttpClient;
import com.zhangyingwei.cockroach2.http.params.ICookieGenerator;
import com.zhangyingwei.cockroach2.monitor.msg.LogMsgHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

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
        QueueHandler queue = new QueueHandler.Builder().build();
        queue.add(new Task("https://baidu.com"));
        queue.add(new Task("https://baidu.com"));
        context.start(queue);
    }

    @Test
    public void testThreadSleepConfig() {
        CockroachConfig config = new CockroachConfig()
                .appName("thread test")
                .threadSeep(5000)
                .threadSeepMin(1000)
                .numThread(1)
                .store(HtmlTitleStore.class).runWithJunit();
        CockroachContext context = new CockroachContext(config);
        QueueHandler queue = new QueueHandler.Builder().build();
        for (int i = 0; i < 10; i++) {
            queue.add(new Task("https://baidu.com"));
        }
        context.start(queue);
    }

    private CockroachConfig config;

    @Before
    public void before() {
        config = new CockroachConfig();
    }

    @Test
    public void appName() {
        String name = "test appname";
        config.appName(name);
        Assert.assertEquals(config.getAppName(), name);
    }

    @Test
    public void runWithJunit() {
        config.runWithJunit();
        Assert.assertEquals(config.isRunWithJunit(), true);
    }

    @Test
    public void numThread() {
        config.numThread(10000000);
        Assert.assertEquals(config.getNumThread(), 10000000);
    }

    @Test
    public void threadSeep() {
        config.threadSeep(1000);
        Assert.assertEquals(config.getThreadSleep(), 1000);
    }

    @Test
    public void threadSeepMin() {
        config.threadSeepMin(10);
        Assert.assertEquals(config.getMinThreadSleep(), Integer.valueOf(10));
    }

    @Test
    public void httpClient() throws InstantiationException, IllegalAccessException {
        config.httpClient(COkHttpClient.class);
        Assert.assertTrue(config.newHttpClient() instanceof COkHttpClient);
    }

    @Test
    public void store() throws InstantiationException, IllegalAccessException {
        config.store(HtmlTitleStore.class);
        Assert.assertTrue(config.newStore() instanceof HtmlTitleStore);
    }

    @Test
    public void cookidGenerator() throws InstantiationException, IllegalAccessException {
        config.cookidGenerator(TestCookieGenerator.class);
        Assert.assertTrue(config.newCookieGenerator() instanceof ICookieGenerator);
    }

    @Test
    public void headerGenerator() throws IllegalAccessException, InstantiationException {
        config.headerGenerator(TestHeaderGenerator.class);
        Assert.assertTrue(config.newHeaderGenerators() instanceof List);
    }

    @Test
    public void proxyGenerator() throws InstantiationException, IllegalAccessException {
        config.proxyGenerator(TestProxyGenerator.class);
        Assert.assertTrue(config.newProxyGenerator() instanceof TestProxyGenerator);
    }

    @Test
    public void print() {
        config.print();
    }

    @Test
    public void getLogMsgHandler() {
        LogMsgHandler logHandler = config.getLogMsgHandler();
        Assert.assertNotNull(logHandler);
    }
}