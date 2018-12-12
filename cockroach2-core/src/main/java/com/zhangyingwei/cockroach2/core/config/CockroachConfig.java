package com.zhangyingwei.cockroach2.core.config;

import com.zhangyingwei.cockroach2.common.generators.ICGenerator;
import com.zhangyingwei.cockroach2.common.generators.ICMapGenerator;
import com.zhangyingwei.cockroach2.common.generators.ICStringGenerator;
import com.zhangyingwei.cockroach2.core.store.IStore;
import com.zhangyingwei.cockroach2.core.store.PrintStore;
import com.zhangyingwei.cockroach2.http.ICHttpClient;
import com.zhangyingwei.cockroach2.http.okhttp.COkHttpClient;
import com.zhangyingwei.cockroach2.http.proxy.ProxyInfo;
import lombok.Getter;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */
public class CockroachConfig {
    @Getter
    private String appName;
    @Getter
    private int numThread;
    @Getter
    private int threadSleep;
    @Getter
    private Boolean autoClose;
    @Getter
    private Class<? extends ICHttpClient> httpClientClass = COkHttpClient.class;
    @Getter
    private Class<? extends IStore> storeClass = PrintStore.class;
    @Getter
    private Class<? extends ICStringGenerator> cookieGeneratorClass;
    @Getter
    private Class<? extends ICMapGenerator> headerGeneratorClass;
    @Getter
    private Class<? extends ICGenerator> proxyGeneratorClass;

    public CockroachConfig appName(String appName) {
        this.appName = appName;
        return this;
    }

    public CockroachConfig numThread(int numThread) {
        this.numThread = numThread;
        return this;
    }

    public CockroachConfig threadSeep(int sleep) {
        this.threadSleep = sleep;
        return this;
    }

    public CockroachConfig autoClose(boolean autoClose) {
        this.autoClose = autoClose;
        return this;
    }

    public CockroachConfig httpClient(Class<? extends ICHttpClient> httpClient) {
        this.httpClientClass = httpClient;
        return this;
    }

    public CockroachConfig store(Class<? extends IStore> store) {
        this.storeClass = store;
        return this;
    }

    public CockroachConfig cookidGenerator(Class<? extends ICStringGenerator> cookieGenerator) {
        this.cookieGeneratorClass = cookieGenerator;
        return this;
    }

    public CockroachConfig headerGenerator(Class<? extends ICMapGenerator> headerGenerator) {
        this.headerGeneratorClass = headerGenerator;
        return this;
    }

    public CockroachConfig proxyGenerator(Class<? extends ICGenerator<ProxyInfo>> proxyGenerator) {
        this.proxyGeneratorClass = proxyGenerator;
        return this;
    }
}
