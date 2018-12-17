package com.zhangyingwei.cockroach2.core.executor;


import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.common.exception.TaskExecuteException;
import com.zhangyingwei.cockroach2.common.generators.ICGenerator;
import com.zhangyingwei.cockroach2.common.utils.IdUtils;
import com.zhangyingwei.cockroach2.core.http.CockroachHttpClient;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.core.store.IStore;
import com.zhangyingwei.cockroach2.http.proxy.ProxyInfo;
import com.zhangyingwei.cockroach2.session.request.CockroachRequest;
import com.zhangyingwei.cockroach2.session.response.CockroachResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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
    protected QueueHandler queue;
    private CockroachHttpClient client;
    private ICGenerator<ProxyInfo> proxy;
    private IStore store;
    private int threadSleep;
    private Thread currentThread = null;

    public TaskExecotor(QueueHandler queue, CockroachHttpClient client, ICGenerator<ProxyInfo> proxy, IStore store, int threadSleep) {
        this.queue = queue;
        this.proxy = proxy;
        this.client = client;
        this.store = store;
        this.threadSleep = threadSleep;
    }

    @Override
    public Task execute() {
        Task task = this.queue.get();
        try {
            if (task != null) {
                if (this.validTask(task)) {
                    CockroachRequest request = new CockroachRequest(task);
                    ProxyInfo proxyInfo = null;
                    if (proxy != null) {
                        proxyInfo = proxy.generate(task);
                    }
                    CockroachResponse response = this.client.proxy(proxyInfo).execute(request);
                    if (response != null && response.isSuccess()) {
                        response.setQueue(this.queue);
                        this.store.store(response);
                        response.close();
                    }
                }
            } else {
                log.debug("take task null");
            }
        } catch (TaskExecuteException e) {
            log.info("execure error with task {}: {}", task, e.getLocalizedMessage());
            if (task.needRetry()) {
                this.queue.add(task);
                log.info("make task retry: {}", task);
            }
        }
        return task;
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
        this.currentThread = Thread.currentThread();
        while (keepRun) {
            try {
                Task task = this.execute();
                if (task == null) {
                    break;
                }
                TimeUnit.MILLISECONDS.sleep(this.threadSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Thread.State getStatus() {
        return this.currentThread.getState();
    }
}
