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
    private ExecutorFactory executorFactory;

    public ExecuterManager(CockroachConfig config) {
        this.config = config;
        this.applicationListener = new ApplicationListener(config.getLogMsgHandler());
    }

    public void start(QueueHandler queue) throws IllegalAccessException, InstantiationException, InterruptedException {
        this.executorFactory = new ExecutorFactory(queue, this.config);
        int numThread = this.config.getNumThread();
        for (int i = 0; i < numThread; i++) {
            TaskExecutor execotor = this.executorFactory.createExecutor(ExecutorFactory.Type.TASK_EXECUTOR);
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

        new Thread(() -> {
            try {
                while (true) {
                    if (this.service.isTerminated()) {
                        AsyncUtils.shutdown();
                        break;
                    }
                    TimeUnit.SECONDS.sleep(1);
                }
                while (true) {
                    if (AsyncUtils.isTerminated()) {
                        this.config.getLogMsgHandler().shutdown();
                        break;
                    }
                    TimeUnit.SECONDS.sleep(1);
                }
                while (true) {
                    if (this.config.getLogMsgHandler().isTerminated()) {
                        this.applicationListener.onStop();
                        latch.countDown();
                        break;
                    }
                    TimeUnit.SECONDS.sleep(1);
                }
                log.info("executor manager stop!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        if (this.config.isRunWithJunit()) {
            latch.await();
        }
    }

    public void stop() {
        executorList.stream().forEach(taskExecutor -> {
            taskExecutor.stop();
        });
        this.applicationListener.onStop();
    }
}
