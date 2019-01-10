package com.zhangyingwei.cockroach2.core.executor;

import com.zhangyingwei.cockroach2.common.Constants;
import com.zhangyingwei.cockroach2.common.utils.IdUtils;
import com.zhangyingwei.cockroach2.common.utils.LogUtils;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
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
    private List<TaskExecutor> executorList;
    private ExecutorFactory executorFactory;

    public ExecutorMonitor(ExecutorService service,
                           QueueHandler queue,
                           CockroachConfig config,
                           List<TaskExecutor> executorList) {
        this.service = service;
        this.queue = queue;
        this.config = config;
        this.executorList = executorList;
        this.executorFactory = new ExecutorFactory(this.queue, this.config);
    }

    @Override
    public void run() {
        Thread.currentThread().setName(Constants.THREAD_NAME_MOINTOR.concat(IdUtils.getId(ExecutorMonitor.class.getName())+""));
        int numThread = this.config.getNumThread();
        while (keepRun) {
            try {
                TimeUnit.SECONDS.sleep(5);
                this.infoPrintMonitor();
                this.executorStatusCheckMonitor();
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

    private void infoPrintMonitor() {
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) this.service;
        int queueSize = poolExecutor.getQueue().size();
        int activeCount = poolExecutor.getActiveCount();
        long completedTaskCount = poolExecutor.getCompletedTaskCount();
        long taskCount = poolExecutor.getTaskCount();
        int waitingExecutor = this.executorList.stream()
                .map(executor -> executor.getThreadState())
                .filter(state -> Thread.State.WAITING.equals(state))
                .collect(Collectors.toList()).size();
        log.info(
                LogUtils.getLineColot("queue({})\twait({})\tactive({})\tcompleted({})\ttask({})\twaiting task({})"),
                queue.size(),
                queueSize,
                activeCount,
                completedTaskCount,
                taskCount,
                waitingExecutor
        );
    }

    /**
     * 检查提交的executor是否已经结束，如果已经结束，则拉起来
     */
    private void executorStatusCheckMonitor() {
        this.executorList.stream()
                .filter(executor -> TaskExecutor.State.OVER.equals(executor.getState())).limit(queue.size()).forEach(taskExecutor -> {
                    this.service.execute(taskExecutor);
                    log.debug(LogUtils.getLineColot("{} was resubmited!"), taskExecutor.getName());
                });
    }

    /**
     * 监控所有task的执行时间，如果超时，则杀掉进程重新提交task
     */
    private void executorTaskMonitor() {
        executorList.forEach(executor -> {
            if (executor.isTaskTimeOut()) {
                log.info(LogUtils.getLineColot("task in {} is timeout!"), executor.getName());
            }
        });
    }

    /**
     * 监控是否所有线程均被阻塞
     * @param numThread
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void executorStatusMonitor(int numThread) throws IllegalAccessException, InstantiationException {
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) this.service;
        int activeCount = poolExecutor.getActiveCount();
        int runningExecutor = this.executorList.stream().filter(taskExecutor -> TaskExecutor.State.RUNNING.equals(taskExecutor.getState())).collect(Collectors.toList()).size();
        int waitingExecutor = this.executorList.stream()
                .map(executor -> executor.getThreadState())
                .filter(state -> Thread.State.WAITING.equals(state))
                .collect(Collectors.toList()).size();
        if (waitingExecutor == this.executorList.size() || (numThread - waitingExecutor) < numThread / 2) {
            if (queue.size() > 0) {
                int tmpNumTherad = numThread;
                for (int i = 0; i < tmpNumTherad; i++) {
                    TaskExecutor tmpTaskExecutor = this.executorFactory.createExecutor(ExecutorFactory.Type.TMP_TASK_EXECUTOR);
                    this.service.execute(tmpTaskExecutor);
                    this.executorList.add(tmpTaskExecutor);
                }
                log.info(LogUtils.getLineColot("submit {} tmp executor!"), tmpNumTherad);
            }
        }
        if (activeCount == 0 && runningExecutor == 0) {
            if (!queue.getWithBlock() && !service.isShutdown()) {
                service.shutdown();
                log.debug(LogUtils.getLineColot("main service shutdown!"));
            }
        }
    }
}