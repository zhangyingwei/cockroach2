package com.zhangyingwei.cockroach2.samples.gushiwen;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.CockroachContext;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.samples.gushiwen.store.GuShiStore;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author zhangyw
 * @date: 2019/1/9
 * @desc:
 */
public class GuShiApplicationWithProperties {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        CockroachConfig config = new CockroachConfig(args[0]);
        CockroachContext context = new CockroachContext(config);
        QueueHandler queue = new QueueHandler.Builder().build();
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
