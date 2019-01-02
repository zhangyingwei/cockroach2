package com.zhangyingwei.cockroach2.monitor.mvc;


import com.blade.Blade;
import com.github.zhangyingwei.solid.config.FileTemplateResourceLoader;
import com.github.zhangyingwei.solid.config.SolidConfiguration;
import com.github.zhangyingwei.solid.template.TemplateResolver;

public class CockroachMonitorApplication {

    private static Blade blade = Blade.of();
    private static SolidConfiguration configuration = new SolidConfiguration(new FileTemplateResourceLoader(
            "/Users/zhangyw/IdeaProjects/cockroach2/cockroach2-monitor/src/main/resources/templates/"
    ));
    private static TemplateResolver resolver = new TemplateResolver(configuration);

    public static void start() {
        System.out.println(System.getProperty("user.dir"));
        blade.bannerText("start web server...");
        blade.get("/", ctx -> ctx.html(resolver.resolve("index.html").render()));
        blade.get("/queue", ctx -> ctx.html(resolver.resolve("queue.html").render()));
        blade.get("/task", ctx -> ctx.html(resolver.resolve("task.html").render()));
        blade.get("/executor", ctx -> ctx.html(resolver.resolve("executor.html").render()));
        blade.start();
    }

    public static void stop() {
        blade.stop();
        System.exit(0);
    }

    public static void main(String[] args) {
        start();
    }
}