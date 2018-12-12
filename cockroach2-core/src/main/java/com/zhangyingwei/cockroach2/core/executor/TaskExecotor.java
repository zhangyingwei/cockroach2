package com.zhangyingwei.cockroach2.core.executor;


import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.common.exception.CockroachUrlNotValidException;
import com.zhangyingwei.cockroach2.common.generators.ICGenerator;
import com.zhangyingwei.cockroach2.common.utils.IdUtils;
import com.zhangyingwei.cockroach2.core.CockroachContext;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.http.CockroachHttpClient;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.core.store.IStore;
import com.zhangyingwei.cockroach2.http.ICHttpClient;
import com.zhangyingwei.cockroach2.http.proxy.ICHttpProxy;
import com.zhangyingwei.cockroach2.http.proxy.ProxyInfo;
import com.zhangyingwei.cockroach2.session.request.CockroachRequest;
import com.zhangyingwei.cockroach2.session.response.CockroachResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */
@Slf4j
public class TaskExecotor implements ICTaskExecutor,Runnable {
    private String name = "exetucor-" + IdUtils.getId("TaskExecotor");
    private Boolean keepRun = true;
    private QueueHandler queue;
    private CockroachHttpClient client;
    private Boolean block;
    private ICGenerator<ProxyInfo> proxy;
    private IStore store;
    private int threadSleep;

    public TaskExecotor(QueueHandler queue, CockroachHttpClient client, ICGenerator<ProxyInfo> proxy, IStore store, Boolean block, int threadSleep) {
        this.queue = queue;
        this.block = block;
        this.proxy = proxy;
        this.client = client;
        this.store = store;
        this.threadSleep = threadSleep;
    }

    @Override
    public void execute() {
        try {
            Task task = this.queue.get(this.block);
            if (task != null) {
                if (this.validTask(task)) {
                    CockroachRequest request = new CockroachRequest(task);
                    ProxyInfo proxyInfo = null;
                    if (proxy != null) {
                        proxyInfo = proxy.generator();
                    }
                    log.debug("before execute...");
                    CockroachResponse response = this.client.proxy(proxyInfo).exetute(request);
                    log.debug("after execite...");
                    this.store.store(response);
                }
            }else {
                log.debug("take task null");
            }
            TimeUnit.MILLISECONDS.sleep(this.threadSleep);
        } catch (CockroachUrlNotValidException | IOException |InterruptedException e) {
            log.info(e.getLocalizedMessage());
        }
    }

    private boolean validTask(Task task) {
        return task.getUrl() != null;
    }

    @Override
    public void stop() {
        this.keepRun = false;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(this.name);
        while (keepRun) {
            this.execute();
        }
    }
}
