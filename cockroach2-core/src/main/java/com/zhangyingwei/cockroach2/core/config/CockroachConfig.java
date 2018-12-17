package com.zhangyingwei.cockroach2.core.config;

import com.zhangyingwei.cockroach2.common.generators.ICGenerator;
import com.zhangyingwei.cockroach2.core.store.IStore;
import com.zhangyingwei.cockroach2.core.store.PrintStore;
import com.zhangyingwei.cockroach2.http.ICHttpClient;
import com.zhangyingwei.cockroach2.http.okhttp.COkHttpClient;
import com.zhangyingwei.cockroach2.http.params.ICookieGenerator;
import com.zhangyingwei.cockroach2.http.params.IHeaderGenerator;
import com.zhangyingwei.cockroach2.http.proxy.ProxyInfo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */
@Slf4j
public class CockroachConfig {
    @Getter
    private String appName = "Cockroach";
    @Getter
    private int numThread = 1;
    @Getter
    private int threadSleep = 500;
    @Getter
    private Class<? extends ICHttpClient> httpClientClass = COkHttpClient.class;
    @Getter
    private Class<? extends IStore> storeClass = PrintStore.class;
    @Getter
    private Class<? extends ICookieGenerator> cookieGeneratorClass;
    @Getter
    private Class<? extends IHeaderGenerator> headerGeneratorClass;
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

    public CockroachConfig httpClient(Class<? extends ICHttpClient> httpClient) {
        this.httpClientClass = httpClient;
        return this;
    }

    public CockroachConfig store(Class<? extends IStore> store) {
        this.storeClass = store;
        return this;
    }

    public CockroachConfig cookidGenerator(Class<? extends ICookieGenerator> cookieGenerator) {
        this.cookieGeneratorClass = cookieGenerator;
        return this;
    }

    public CockroachConfig headerGenerator(Class<? extends IHeaderGenerator> headerGenerator) {
        this.headerGeneratorClass = headerGenerator;
        return this;
    }

    public CockroachConfig proxyGenerator(Class<? extends ICGenerator<ProxyInfo>> proxyGenerator) {
        this.proxyGeneratorClass = proxyGenerator;
        return this;
    }

    public void print() {
        log.info("appName: {}", appName);
        log.info("numThread: {}", numThread);
        log.info("threadSleep: {}", threadSleep);
        log.info("httpClientClass: {}", httpClientClass);
        log.info("storeClass: {}", storeClass);
        log.info("cookieGeneratorClass: {}", cookieGeneratorClass);
        log.info("headerGeneratorClass: {}", headerGeneratorClass);
        log.info("proxyGeneratorClass: {}", proxyGeneratorClass);
    }
}
