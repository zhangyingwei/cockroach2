package com.zhangyingwei.cockroach2.samples.generator;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.CockroachContext;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.core.store.HtmlTitleStore;
import com.zhangyingwei.cockroach2.http.params.IHeaderGenerator;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public class HeaderGeneratorApplication {
    public static void main(String[] args) {
        log.info(HeaderGeneratorApplication.MyHeaderGenerator.class.getName());
        CockroachConfig config = new CockroachConfig()
                .appName("cookie generator")
                .store(HtmlTitleStore.class)
                .headerGenerator(HeaderGeneratorApplication.MyHeaderGenerator.class);
        CockroachContext context = new CockroachContext(config);
        QueueHandler queueHandler = new QueueHandler.Builder().withBlock(false).build();
        for (int i = 0; i < 10; i++) {
            queueHandler.add(new Task("https://baidu.com"));
        }
        context.start(queueHandler);
    }

    public static class MyHeaderGenerator implements IHeaderGenerator {
        @Override
        public Map generate(Task task) {
            System.out.println("generate an header with task: " + task);
            return new HashMap();
        }
    }

}
