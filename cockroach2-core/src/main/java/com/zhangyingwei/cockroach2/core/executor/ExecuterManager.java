package com.zhangyingwei.cockroach2.core.executor;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.common.async.AsyncUtils;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.http.CockroachHttpClient;
import com.zhangyingwei.cockroach2.core.listener.ApplicationListener;
import com.zhangyingwei.cockroach2.core.listener.ICListener;
import com.zhangyingwei.cockroach2.core.listener.TaskExecuteListener;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.http.params.IHeaderGenerator;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */
@Slf4j
public class ExecuterManager {
    private CockroachConfig config;
    private ExecutorService service = Executors.newCachedThreadPool();
    private List<TaskExecutor> executorList = new ArrayList<>();
    private ApplicationListener applicationListener;
    private CountDownLatch latch = new CountDownLatch(1);

    public ExecuterManager(CockroachConfig config) {
        this.config = config;
        this.applicationListener = new ApplicationListener();
    }

    public void start(QueueHandler queue) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, InterruptedException {
        int numThread = this.config.getNumThread();
        for (int i = 0; i < numThread; i++) {
            TaskExecutor execotor = new TaskExecutor(
                    queue,
                    new CockroachHttpClient(
                            this.config.newHttpClient(),
                            this.config.newCookieGenerator(),
                            this.config.newHeaderGenerators()
                    ),
                    this.config.newProxyGenerator(),
                    this.config.newStore(),
                    this.config.getThreadSleep(),
                    new TaskExecuteListener()
            );
            this.service.execute(execotor);
            executorList.add(execotor);
        }
        // Monitor thread
        Thread monitorThread = new Thread(new ExecutorMonitor(
            this.service,queue,this.config,this.executorList
        ));
        monitorThread.setDaemon(true);
        monitorThread.start();
        this.applicationListener.onStart();
        while (true) {
            if (this.service.isTerminated()) {
                AsyncUtils.shutdown();
                break;
            }
            TimeUnit.SECONDS.sleep(2);
        }
        while (true) {
            if (AsyncUtils.isTerminated()) {
                this.applicationListener.onStop();
                latch.countDown();
                break;
            }
            TimeUnit.SECONDS.sleep(2);
        }
        latch.await();
        System.out.println("executor manager stop!");
    }

    public void stop() {
        executorList.stream().forEach(taskExecutor -> {
            taskExecutor.stop();
        });
        this.applicationListener.onStop();
    }
}
