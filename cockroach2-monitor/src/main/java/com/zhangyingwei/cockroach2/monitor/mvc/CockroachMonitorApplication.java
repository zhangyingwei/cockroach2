package com.zhangyingwei.cockroach2.monitor.mvc;


import com.blade.Blade;
import com.github.zhangyingwei.solid.config.FileTemplateResourceLoader;
import com.github.zhangyingwei.solid.config.SolidConfiguration;
import com.github.zhangyingwei.solid.template.TemplateResolver;
import com.zhangyingwei.cockroach2.monitor.mvc.service.QueueService;
import com.zhangyingwei.cockroach2.monitor.mvc.service.TaskService;

import java.io.File;

public class CockroachMonitorApplication {

    private Blade blade = Blade.of();
    private SolidConfiguration configuration;
    private TemplateResolver resolver;
    private QueueService queueService = new QueueService();
    private TaskService taskService = new TaskService();

    public CockroachMonitorApplication() {
        this.configuration = new SolidConfiguration(new FileTemplateResourceLoader(
                new File(CockroachMonitorApplication.class.getResource("").getPath())
                        .getParentFile()
                        .getParentFile()
                        .getParentFile()
                        .getParentFile()
                        .getParentFile().getAbsolutePath().concat(File.separator)
        ));
        this.resolver = new TemplateResolver(this.configuration);
    }

    public void start() {
        blade.bannerText("start web server...");
        blade.get("/", ctx -> ctx.html(resolver.resolve("templates/index.html").render()));
        blade.get("/queue", ctx -> ctx.html(resolver.resolve("templates/queue.html").render()));
        blade.get("/task", ctx -> ctx.html(resolver.resolve("templates/task.html").render()));
        blade.get("/executor", ctx -> ctx.html(resolver.resolve("templates/executor.html").render()));
        //ajax
        blade.get("/queue/info", ctx -> ctx.json(this.queueService.getQueryInfo()));
        blade.get("/task/info", ctx -> ctx.json(this.taskService.getQueryInfo()));
        blade.start();
    }

    public void stop() {
        blade.stop();
        System.exit(0);
    }

    public static void main(String[] args) {
        new CockroachMonitorApplication().start();
    }
}