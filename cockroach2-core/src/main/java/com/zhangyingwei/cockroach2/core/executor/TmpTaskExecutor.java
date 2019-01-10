package com.zhangyingwei.cockroach2.core.executor;


import com.zhangyingwei.cockroach2.common.Constants;
import com.zhangyingwei.cockroach2.common.async.AsyncManager;
import com.zhangyingwei.cockroach2.common.generators.ICGenerator;
import com.zhangyingwei.cockroach2.common.utils.IdUtils;
import com.zhangyingwei.cockroach2.common.utils.ThreadSleepTool;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.http.CockroachHttpClient;
import com.zhangyingwei.cockroach2.core.listener.TaskExecuteListener;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.core.store.IStore;
import com.zhangyingwei.cockroach2.http.proxy.ProxyInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */
@Slf4j
public class TmpTaskExecutor extends TaskExecutor{

    public TmpTaskExecutor(QueueHandler queue, CockroachConfig config) throws IllegalAccessException, InstantiationException {
        super(queue, config);
    }

    @Override
    public void run() {
        super.currentThread = Thread.currentThread();
        Thread.currentThread().setName(Constants.THREAD_NAME_EXECUTER_TMP.concat(IdUtils.getId("executor-tmp")+""));
        try{
            super.state = State.RUNNING;
            asyncManager.doVoidMethodAsync(() -> super.taskExecuteListener.start(this.getName()));
            super.execute();
        }finally {
            super.state = State.DEAD;
            asyncManager.doVoidMethodAsync(() -> taskExecuteListener.stop(this.getName()));
        }
    }
}
