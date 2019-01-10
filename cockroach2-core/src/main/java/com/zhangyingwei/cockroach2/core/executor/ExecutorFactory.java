package com.zhangyingwei.cockroach2.core.executor;


import com.zhangyingwei.cockroach2.common.utils.ThreadSleepTool;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.http.CockroachHttpClient;
import com.zhangyingwei.cockroach2.core.listener.TaskExecuteListener;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;

/**
 * executor factory
 * create taskexecutor or tmptaskexecutor
 */
public class ExecutorFactory {

    private QueueHandler queue;
    private CockroachConfig config;
    public ExecutorFactory(QueueHandler queue, CockroachConfig config) {
        this.queue = queue;
        this.config = config;
    }

    enum Type {
        TASK_EXECUTOR,TMP_TASK_EXECUTOR
    }

    public TaskExecutor createExecutor(Type type) throws IllegalAccessException, InstantiationException {
        if (Type.TASK_EXECUTOR.equals(type)) {
            return createTaskExecutor();
        } else if (Type.TMP_TASK_EXECUTOR.equals(type)) {
            return createTmpTaskExecutor();
        } else {
            return null;
        }
    }

    private TaskExecutor createTmpTaskExecutor() throws InstantiationException, IllegalAccessException {
        return new TmpTaskExecutor(queue,this.config);
    }

    private TaskExecutor createTaskExecutor() throws InstantiationException, IllegalAccessException {
        return new TaskExecutor(
                queue,
                config
        );
    }
}
