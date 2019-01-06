package com.zhangyingwei.cockroach2.core.executor;


import com.zhangyingwei.cockroach2.common.Constants;
import com.zhangyingwei.cockroach2.common.generators.ICGenerator;
import com.zhangyingwei.cockroach2.common.utils.IdUtils;
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
    public TmpTaskExecutor(QueueHandler queue, CockroachHttpClient client, ICGenerator<ProxyInfo> proxy, IStore store, int threadSleep, TaskExecuteListener taskExecuteListener) {
        super(queue, client, proxy, store, threadSleep,taskExecuteListener);
    }

    @Override
    public void run() {
        Thread.currentThread().setName(Constants.THREAD_NAME_EXECUTER_TMP.concat(IdUtils.getId("executor-tmp")+""));
        super.execute();
        super.state = State.OVER;
    }
}
