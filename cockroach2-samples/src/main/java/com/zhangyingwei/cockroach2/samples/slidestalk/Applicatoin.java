package com.zhangyingwei.cockroach2.samples.slidestalk;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.CockroachContext;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.samples.slidestalk.store.MainStore;

/**
 * @author zhangyw
 * @date: 2018/12/14
 * @desc:
 */
public class Applicatoin {
    public static void main(String[] args) {
        CockroachConfig config = new CockroachConfig()
                .appName("示说网爬虫")
                .autoClose(true)
                .threadSeep(1000)
                .numThread(1)
                .store(MainStore.class);
        CockroachContext context = new CockroachContext(config);
        context.start(getQueue());
    }

    private static QueueHandler getQueue() {
        QueueHandler queueHandler = QueueHandler.initWithDefaultQueue();
        queueHandler.withFilter(task -> {
            return task.getUrl() != null && !task.getUrl().equals("https://www.slidestalk.com");
        });
        queueHandler.add(new Task("https://www.slidestalk.com/categories/1","ss"));
        return queueHandler;
    }
}
