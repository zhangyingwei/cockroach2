package com.zhangyingwei.cockroach2.samples.gushiwen;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.CockroachContext;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.monitor.msg.consumer.CockroachMonitorConsumer;
import com.zhangyingwei.cockroach2.samples.gushiwen.store.GuShiStore;

import java.util.HashMap;

/**
 * @author zhangyw
 * @date: 2019/1/9
 * @desc:
 */
public class GuShiApplication {
    public static void main(String[] args) {
        CockroachConfig config = new CockroachConfig()
                .appName("古诗文网").numThread(10).threadSeep(5000).threadSeepMin(1000).store(GuShiStore.class).addLogConsumer(CockroachMonitorConsumer.class);
        CockroachContext context = new CockroachContext(config);
        QueueHandler queue = new QueueHandler.Builder().withBlock(true).build();
        Task task = new Task("https://so.gushiwen.org/authors/default.aspx","gs");
        task.setParams(new HashMap<String, String>(){
            {
                put("p", "1");
            }
        });
        queue.add(task);
        context.start(queue);
    }
}
