package com.zhangyingwei.cockroach2.samples.gushiwen;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.CockroachContext;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
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
                .appName("古诗文网").numThread(1).threadSeep(1000).store(GuShiStore.class);
        CockroachContext context = new CockroachContext(config);
        QueueHandler queue = QueueHandler.initWithDefaultQueue();
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
