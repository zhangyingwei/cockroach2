package com.zhangyingwei.cockroach2.samples.slidestalk;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.CockroachContext;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.monitor.msg.LogMsgHandler;
import com.zhangyingwei.cockroach2.monitor.msg.Msg;
import com.zhangyingwei.cockroach2.monitor.msg.consumer.CockroachMonitorConsumer;
import com.zhangyingwei.cockroach2.monitor.msg.consumer.CockroachTaskConsumer;
import com.zhangyingwei.cockroach2.monitor.msg.consumer.DefaultCockroachConsumer;
import com.zhangyingwei.cockroach2.monitor.msg.consumer.ICMsgConsumer;
import com.zhangyingwei.cockroach2.samples.slidestalk.store.MainStore;

/**
 * @author zhangyw
 * @date: 2018/12/14
 * @desc:
 */
public class Application {
    public static void main(String[] args) {
        CockroachConfig config = new CockroachConfig()
                .appName("示说网爬虫")
                .threadSeep(1000)
                .threadSeepMin(300)
                .numThread(8)
                .store(MainStore.class)
                .addLogConsumer(CockroachMonitorConsumer.class);
        CockroachContext context = new CockroachContext(config);
        context.start(getQueue());
    }

    private static QueueHandler getQueue() {
        QueueHandler queueHandler = new QueueHandler.Builder(10000).withBlock(false).limit(50L)
                .withFilter(task -> task.getUrl() != null && !task.getUrl().equals("https://www.slidestalk.com")).build();
        queueHandler.add(new Task("https://www.slidestalk.com/categories/1","ss"));
        return queueHandler;
    }
}
