package com.zhangyingwei.cockroach2.core.executor;

import com.zhangyingwei.cockroach2.common.async.AsyncManager;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.listener.GlobalListener;
import com.zhangyingwei.cockroach2.core.listener.ICListener;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import lombok.extern.slf4j.Slf4j;

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
    private GlobalListener listener;
    private CountDownLatch latch = new CountDownLatch(1);
    private ExecutorFactory executorFactory;
    private AsyncManager asyncManager;

    public ExecuterManager(CockroachConfig config) {
        this.config = config;
        this.asyncManager = config.getAsyncManager();
        this.listener = new GlobalListener(this.config);
    }

    public void start(QueueHandler queue) throws IllegalAccessException, InstantiationException, InterruptedException {
        this.listener.action(ICListener.ListenerType.APPLICATION_START, null);
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

        /**
         * 监控所有线程池是否都执行完毕的线程
         */
        new Thread(() -> {
            try {
                while (true) {
                    if (this.service.isTerminated()) {
                        this.asyncManager.shutdown();
                        break;
                    }
                    TimeUnit.SECONDS.sleep(1);
                }
                while (true) {
                    if (this.asyncManager.isTerminated()) {
                        this.listener.action(ICListener.ListenerType.APPLICATION_STOP, null);
                        this.config.getLogMsgHandler().shutdown();
                        break;
                    }
                    TimeUnit.SECONDS.sleep(1);
                }
                while (true) {
                    if (this.config.getLogMsgHandler().isTerminated()) {
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
        this.listener.action(ICListener.ListenerType.APPLICATION_STOP, null);
    }
}
