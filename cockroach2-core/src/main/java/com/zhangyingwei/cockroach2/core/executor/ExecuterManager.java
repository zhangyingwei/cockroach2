package com.zhangyingwei.cockroach2.core.executor;

import com.zhangyingwei.cockroach2.common.generators.ICGenerator;
import com.zhangyingwei.cockroach2.common.utils.IdUtils;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.http.CockroachHttpClient;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.http.params.ICookieGenerator;
import com.zhangyingwei.cockroach2.http.params.IHeaderGenerator;
import com.zhangyingwei.cockroach2.http.proxy.ProxyInfo;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */
@Slf4j
public class ExecuterManager {
    private CockroachConfig config;
    private ExecutorService service = Executors.newCachedThreadPool();
    private List<TaskExecotor> executorList = new ArrayList<>();

    public ExecuterManager(CockroachConfig config) {
        this.config = config;
    }

    public void start(QueueHandler queue) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        int numThread = this.config.getNumThread();
        ICookieGenerator cookieGenerator = (ICookieGenerator) this.newInstance(this.config.getCookieGeneratorClass());
        IHeaderGenerator headerGenerator = (IHeaderGenerator) this.newInstance(this.config.getHeaderGeneratorClass());
        for (int i = 0; i < numThread; i++) {
            TaskExecotor execotor = new TaskExecotor(
                    queue,
                    new CockroachHttpClient(
                            this.config.getHttpClientClass().newInstance(), cookieGenerator, headerGenerator
                    ),
                    this.createProxy(),
                    this.config.getStoreClass().newInstance(),
                    this.config.getThreadSleep()
            );
            this.service.execute(execotor);
            executorList.add(execotor);
        }
        // Monitor thread
        Thread monitorThread = new Thread(new ExecutorMonitor(
            this.service,queue,this.config, cookieGenerator, headerGenerator
        ));
        monitorThread.setDaemon(true);
        monitorThread.start();
    }

    private ICGenerator newInstance(Class<? extends ICGenerator> generatorClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (generatorClass != null) {
            Constructor constructor = generatorClass.getConstructor();
            return (ICGenerator) constructor.newInstance();
        }
        return null;
    }

    private ICGenerator<ProxyInfo> createProxy() throws IllegalAccessException, InstantiationException {
        if (this.config.getProxyGeneratorClass() != null) {
            return this.config.getProxyGeneratorClass().newInstance();
        }
        return null;
    }

    class ExecutorMonitor implements Runnable {
        private final QueueHandler queue;
        private final CockroachConfig config;
        private final ExecutorService service;
        private final ICookieGenerator cookieGenerator;
        private final IHeaderGenerator headerGenerator;
        private boolean keepRun = true;
        public ExecutorMonitor(ExecutorService service, QueueHandler queue, CockroachConfig config, ICookieGenerator cookieGenerator, IHeaderGenerator headerGenerator) {
            this.service = service;
            this.queue = queue;
            this.config = config;
            this.cookieGenerator = cookieGenerator;
            this.headerGenerator = headerGenerator;
        }

        @Override
        public void run() {
            Thread.currentThread().setName("emonitor-".concat(IdUtils.getId("EMonitor")+""));
            int numThread = this.config.getNumThread();
            while (keepRun) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) this.service;
                    int queueSize = poolExecutor.getQueue().size();
                    int activeCount = poolExecutor.getActiveCount();
                    long completedTaskCount = poolExecutor.getCompletedTaskCount();
                    long taskCount = poolExecutor.getTaskCount();
                    int runnableExecutor = executorList.stream().map(executor -> executor.getStatus()).filter(state -> Thread.State.RUNNABLE.equals(state)).collect(Collectors.toList()).size();
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
                                                this.config.getHttpClientClass().newInstance(), cookieGenerator, headerGenerator
                                        ),
                                        createProxy(),
                                        this.config.getStoreClass().newInstance(),
                                        this.config.getThreadSleep()
                                ));
                            }
                            log.info("submit {} tmp executor!", tmpNumTherad);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
