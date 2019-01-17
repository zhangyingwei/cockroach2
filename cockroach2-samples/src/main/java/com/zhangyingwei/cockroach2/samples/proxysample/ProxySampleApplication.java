package com.zhangyingwei.cockroach2.samples.proxysample;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.CockroachContext;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;

/**
 * @author zhangyw
 * @date: 2019/1/17
 * @desc:
 */
public class ProxySampleApplication {
    public static void main(String[] args) {
        CockroachConfig config = new CockroachConfig().numThread(1).proxyGenerator(SelfProxyGenerator.class);
        CockroachContext context = new CockroachContext(config);
        QueueHandler queue = new QueueHandler.Builder().build();
        queue.add(new Task("https://baidu.com/"));
        context.start(queue);
    }
}
