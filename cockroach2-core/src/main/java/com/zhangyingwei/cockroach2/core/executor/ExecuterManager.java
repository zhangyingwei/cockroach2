package com.zhangyingwei.cockroach2.core.executor;

import com.zhangyingwei.cockroach2.common.generators.ICGenerator;
import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.http.CockroachHttpClient;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import com.zhangyingwei.cockroach2.http.proxy.ProxyInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */
@Slf4j
public class ExecuterManager {
    private CockroachConfig config;
    private ExecutorService service = Executors.newCachedThreadPool();

    public ExecuterManager(CockroachConfig config) {
        this.config = config;
    }

    public void start(QueueHandler queue) throws IllegalAccessException, InstantiationException {
        int numThread = this.config.getNumThread();
        for (int i = 0; i < numThread; i++) {
            service.execute(new TaskExecotor(
                    queue,
                    new CockroachHttpClient(this.config.getHttpClientClass().newInstance()),
                    this.createProxy(),
                    this.config.getStoreClass().newInstance(),
                    this.config.getAutoClose(),
                    this.config.getThreadSleep()
            ));
        }
        service.shutdown();
    }

    private ICGenerator<ProxyInfo> createProxy() throws IllegalAccessException, InstantiationException {
        if (this.config.getProxyGeneratorClass() != null) {
            return this.config.getProxyGeneratorClass().newInstance();
        }
        return null;
    }
}
