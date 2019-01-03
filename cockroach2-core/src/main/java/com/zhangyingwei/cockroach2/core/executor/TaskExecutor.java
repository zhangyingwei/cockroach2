package com.zhangyingwei.cockroach2.core.executor;


import com.zhangyingwei.cockroach2.common.Constants;
import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.common.exception.TaskExecuteException;
import com.zhangyingwei.cockroach2.common.generators.ICGenerator;
import com.zhangyingwei.cockroach2.common.async.AsyncUtils;
import com.zhangyingwei.cockroach2.common.utils.IdUtils;
import com.zhangyingwei.cockroach2.core.http.CockroachHttpClient;
import com.zhangyingwei.cockroach2.core.listener.TaskExecuteListener;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.core.store.IStore;
import com.zhangyingwei.cockroach2.http.proxy.ProxyInfo;
import com.zhangyingwei.cockroach2.session.request.CockroachRequest;
import com.zhangyingwei.cockroach2.session.response.CockroachResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */
@Slf4j
public class TaskExecutor implements ICTaskExecutor,Runnable {
    private final TaskExecuteListener taskExecuteListener;
    private String name = Constants.THREAD_NAME_EXECUTER + IdUtils.getId("TaskExecotor");
    private Boolean keepRun = true;
    protected QueueHandler queue;
    private final CockroachHttpClient client;
    private final ICGenerator<ProxyInfo> proxy;
    private final IStore store;
    private final int threadSleep;
    private Thread currentThread = null;

    public TaskExecutor(
            QueueHandler queue,
            CockroachHttpClient client,
            ICGenerator<ProxyInfo> proxy,
            IStore store,
            int threadSleep,
            TaskExecuteListener taskExecuteListener) {
        this.queue = queue;
        this.proxy = proxy;
        this.client = client;
        this.store = store;
        this.threadSleep = threadSleep;
        this.taskExecuteListener = taskExecuteListener;
    }

    @Override
    public Task execute() {
        Task task = this.queue.get();
        try {
            if (task != null) {
                //listener
                AsyncUtils.doVoidMethodAsync(() -> taskExecuteListener.before(task));
                if (this.validTask(task.statu(Task.Statu.VALID))) {
                    CockroachRequest request = new CockroachRequest(task.statu(Task.Statu.EXECUTE));
                    ProxyInfo proxyInfo = null;
                    if (proxy != null) {
                        proxyInfo = proxy.generate(task);
                    }
                    AsyncUtils.doVoidMethodAsync(() -> taskExecuteListener.execute(task));
                    CockroachResponse response = this.client.proxy(proxyInfo).execute(request);
                    if (response != null && response.isSuccess()) {
                        response.setQueue(this.queue);
                        task.statu(Task.Statu.STORE);
                        AsyncUtils.doVoidMethodAsync(() -> taskExecuteListener.store(task));
                        this.store.store(response);
                        response.close();
                        task.statu(Task.Statu.FINISH);
                        AsyncUtils.doVoidMethodAsync(() -> taskExecuteListener.success(task));
                    }
                }
                //listener
                AsyncUtils.doVoidMethodAsync(() -> taskExecuteListener.after(task));
            } else {
                log.debug("task is null!");
            }
        } catch (TaskExecuteException e) {
            AsyncUtils.doVoidMethodAsync(() -> taskExecuteListener.failed(task));
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
        taskExecuteListener.start(
                this.name,
                this.client.getClient() == null? null:this.client.getClient().getClass(),
                this.proxy == null?null:this.proxy.getClass(),
                this.store == null?null:this.store.getClass(),
                threadSleep
        );
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
        taskExecuteListener.stop(this.name);
    }

    public Thread.State getStatus() {
        return this.currentThread.getState();
    }

    public boolean isTaskTimeOut() {
        return false;
    }
}
