package com.zhangyingwei.cockroach2.monitor;

import com.zhangyingwei.cockroach2.db.CockroachDb;
import com.zhangyingwei.cockroach2.monitor.http.server.CockroachHttpServer;
import com.zhangyingwei.cockroach2.monitor.http.server.action.*;

import java.util.concurrent.CountDownLatch;

public class MonitroServer {

    private CockroachDb cockroachDb;

    public MonitroServer(CockroachDb cockroachDb) {
        this.cockroachDb = cockroachDb;
    }

    public void start() {
        CockroachHttpServer server = new CockroachHttpServer();
        server.registeAction("/",new MainIndexAction(cockroachDb));
        server.registeAction("/index",new MainIndexAction(cockroachDb));
        server.registeAction("/index/taskinfo",new MainTaskInfoAction(cockroachDb));
        server.registeAction("/index/executorinfo",new MainExecutorInfoAction(cockroachDb));
        server.registeAction("/task",new TaskIndexAction(cockroachDb));
        server.registeAction("/task/taskinfo",new TaskInfoAction(cockroachDb));
        server.registeAction("/executor",new ExecutorIndexAction(cockroachDb));
        server.registeAction("/executor/infos",new ExecutorInfoAction(cockroachDb));
        server.start();
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        new MonitroServer(null).start();
        latch.await();
    }
}
