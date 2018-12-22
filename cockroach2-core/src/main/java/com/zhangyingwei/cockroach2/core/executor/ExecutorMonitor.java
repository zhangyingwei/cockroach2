package com.zhangyingwei.cockroach2.core.executor;

import com.zhangyingwei.cockroach2.common.utils.IdUtils;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.http.CockroachHttpClient;
import com.zhangyingwei.cockroach2.core.listener.TaskExecuteListener;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class ExecutorMonitor implements Runnable {
    private final QueueHandler queue;
    private final CockroachConfig config;
    private final ExecutorService service;
    private boolean keepRun = true;
    private List<TaskExecotor> executorList;

    public ExecutorMonitor(ExecutorService service,
                           QueueHandler queue,
                           CockroachConfig config,
                           List<TaskExecotor> executorList) {
        this.service = service;
        this.queue = queue;
        this.config = config;
        this.executorList = executorList;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("emonitor-".concat(IdUtils.getId("EMonitor")+""));
        int numThread = this.config.getNumThread();
        while (keepRun) {
            try {
                TimeUnit.SECONDS.sleep(10);
                this.executorStatusMonitor(numThread);
                this.executorTaskMonitor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 监控所有task的执行时间，如果超时，则杀掉进程重新提交task
     */
    private void executorTaskMonitor() {
        executorList.forEach(executor -> {
            if (executor.isTaskTimeOut()) {

            }
        });
    }

    /**
     * 监控是否所有线程均
     * @param numThread
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void executorStatusMonitor(int numThread) throws IllegalAccessException, InstantiationException {
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) this.service;
        int queueSize = poolExecutor.getQueue().size();
        int activeCount = poolExecutor.getActiveCount();
        long completedTaskCount = poolExecutor.getCompletedTaskCount();
        long taskCount = poolExecutor.getTaskCount();
        int runnableExecutor = this.executorList.stream().map(executor -> executor.getStatus()).filter(state -> Thread.State.RUNNABLE.equals(state)).collect(Collectors.toList()).size();
        if (runnableExecutor == 0) {
            log.info(
                    "queue size: {}, wait size:{},active size:{},completed size:{},task count:{}",
                    queue.size(),
                    queueSize,
                    activeCount,
                    completedTaskCount,
                    taskCount
            );
            if (queue.size() > 0) {
                int tmpNumTherad = numThread / 2;
                if (tmpNumTherad < 1) {
                    tmpNumTherad = 1;
                }
                for (int i = 0; i < tmpNumTherad; i++) {
                    this.service.execute(new TmpTaskExecotor(
                            queue,
                            new CockroachHttpClient(
                                    this.config.newHttpClient(), this.config.newCookieGenerator(), this.config.newHeaderGenerators()
                            ),
                            this.config.newProxyGenerator(),
                            this.config.newStore(),
                            this.config.getThreadSleep(),
                            new TaskExecuteListener()
                    ));
                }
                log.info("submit {} tmp executor!", tmpNumTherad);
            }
        }
    }
}