package com.zhangyingwei.cockroach2.core.executor;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.http.CockroachHttpClient;
import com.zhangyingwei.cockroach2.core.listener.ICListener;
import com.zhangyingwei.cockroach2.core.listener.TaskExecuteListener;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.http.params.IHeaderGenerator;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

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
        for (int i = 0; i < numThread; i++) {
            TaskExecotor execotor = new TaskExecotor(
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
    }
}
