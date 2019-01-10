package com.zhangyingwei.cockroach2.samples.generator;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.CockroachContext;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.core.store.HtmlTitleStore;
import com.zhangyingwei.cockroach2.http.params.ICookieGenerator;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CookieGeneratorApplication {
    public static void main(String[] args) {
        log.info(CookieGeneratorApplication.MyCookieGenerator.class.getName());
        CockroachConfig config = new CockroachConfig()
                .appName("cookie generator")
                .store(HtmlTitleStore.class)
                .cookidGenerator(CookieGeneratorApplication.MyCookieGenerator.class);
        CockroachContext context = new CockroachContext(config);
        QueueHandler queueHandler = new QueueHandler.Builder().withBlock(false).build();
        for (int i = 0; i < 10; i++) {
            queueHandler.add(new Task("https://baidu.com"));
        }
        context.start(queueHandler);
    }

    public static class MyCookieGenerator implements ICookieGenerator {
        @Override
        public String generate(Task task) {
            System.out.println("generate an cookie with task: " + task);
            return "";
        }
    }

}
