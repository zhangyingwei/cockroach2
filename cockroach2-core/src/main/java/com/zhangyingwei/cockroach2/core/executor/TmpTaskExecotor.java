package com.zhangyingwei.cockroach2.core.executor;


import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.common.exception.TaskExecuteException;
import com.zhangyingwei.cockroach2.common.generators.ICGenerator;
import com.zhangyingwei.cockroach2.common.utils.IdUtils;
import com.zhangyingwei.cockroach2.core.http.CockroachHttpClient;
import com.zhangyingwei.cockroach2.core.listener.TaskExecuteListener;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.core.store.IStore;
import com.zhangyingwei.cockroach2.http.proxy.ProxyInfo;
import com.zhangyingwei.cockroach2.session.request.CockroachRequest;
import com.zhangyingwei.cockroach2.session.response.CockroachResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */
@Slf4j
public class TmpTaskExecotor extends TaskExecotor{
    public TmpTaskExecotor(QueueHandler queue, CockroachHttpClient client, ICGenerator<ProxyInfo> proxy, IStore store, int threadSleep, TaskExecuteListener taskExecuteListener) {
        super(queue, client, proxy, store, threadSleep,taskExecuteListener);
    }

    @Override
    public void run() {
        Thread.currentThread().setName("executor-tmp-".concat(IdUtils.getId("executor-tmp")+""));
        super.execute();
    }
}
