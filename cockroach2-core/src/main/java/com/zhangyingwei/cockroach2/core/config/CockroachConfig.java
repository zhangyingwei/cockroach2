package com.zhangyingwei.cockroach2.core.config;

import com.zhangyingwei.cockroach2.common.ConfigConstants;
import com.zhangyingwei.cockroach2.common.Constants;
import com.zhangyingwei.cockroach2.common.generators.ICGenerator;
import com.zhangyingwei.cockroach2.common.utils.ClassUtils;
import com.zhangyingwei.cockroach2.common.utils.LogUtils;
import com.zhangyingwei.cockroach2.common.utils.NameUtil;
import com.zhangyingwei.cockroach2.common.utils.PropertiesUtils;
import com.zhangyingwei.cockroach2.core.store.IStore;
import com.zhangyingwei.cockroach2.core.store.PrintStore;
import com.zhangyingwei.cockroach2.http.ICHttpClient;
import com.zhangyingwei.cockroach2.http.okhttp.COkHttpClient;
import com.zhangyingwei.cockroach2.http.params.DefaultHeaderGenerator;
import com.zhangyingwei.cockroach2.http.params.ICookieGenerator;
import com.zhangyingwei.cockroach2.http.params.IHeaderGenerator;
import com.zhangyingwei.cockroach2.http.proxy.ProxyInfo;
import com.zhangyingwei.cockroach2.monitor.msg.LogMsgHandler;
import com.zhangyingwei.cockroach2.monitor.msg.consumer.ICMsgConsumer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */
@Slf4j
public class CockroachConfig {
    private boolean runWithJunit = false;
    /**
     * app name
     */
    @Getter
    private String appName = "Cockroach";
    /**
     * 工作线程数
     */
    @Getter
    private int numThread = 1;
    /**
     * 线程睡眠时间
     */
    @Getter
    private int threadSleep = 500;

    /**
     * http client class
     * 用于反射创建 http 客户端
     */
    private Class<? extends ICHttpClient> httpClientClass = COkHttpClient.class;
    /**
     * store 类
     * 用户反射创建 store 类
     */
    private Class<? extends IStore> storeClass = PrintStore.class;
    /**
     * cookie 生成去类
     * 用于反射生成 cookie 生成器
     */
    private Class<? extends ICookieGenerator> cookieGeneratorClass;
    private Class<? extends IHeaderGenerator> headerGeneratorClass;
    private Class<? extends ICGenerator> proxyGeneratorClass;
    private LogMsgHandler logMsgHandler = new LogMsgHandler();

    public CockroachConfig(){}

    /**
     * load config by properties
     * @param properties
     */
    public CockroachConfig(Properties properties) throws ClassNotFoundException {
        this.appName(PropertiesUtils.getOrDefault(properties,ConfigConstants.KEY_CONFIG_APPNAME,NameUtil.getName()));
        this.threadSeep(PropertiesUtils.getOrDefault(properties, ConfigConstants.KEY_CONFIG_THREADSLEEP, ConfigConstants.THREAD_SLEEP));
        this.numThread(PropertiesUtils.getOrDefault(properties, ConfigConstants.KEY_CONFIG_NUMTHREAD, ConfigConstants.THREAD_NUM));
        this.store(ClassUtils.getOrNullClass(
                PropertiesUtils.getOrDefault(properties, ConfigConstants.KEY_CONFIG_STORE, ConfigConstants.STORE)
        ));
        this.proxyGenerator(ClassUtils.getOrNullClass(
                PropertiesUtils.getOrDefault(properties, ConfigConstants.KEY_CONFIG_PROXYGENERATOR, null)
        ));
        this.headerGenerator(ClassUtils.getOrNullClass(
                PropertiesUtils.getOrDefault(properties, ConfigConstants.KEY_CONFIG_HEADERGENERATOR, null)
        ));
        this.httpClient(ClassUtils.getOrNullClass(
                PropertiesUtils.getOrDefault(properties, ConfigConstants.KEY_CONFIG_HTTPCLIENT, ConfigConstants.HTTPCLIENT)
        ));
        List<Class<? extends ICMsgConsumer>> consumers = ClassUtils.getClasses(
                PropertiesUtils.getOrDefault(properties, ConfigConstants.KEY_CONFIG_LOGCONSUMERS, null)
        );
        consumers.forEach(consumer -> {
            this.addLogConsumer(consumer);
        });
    }

    public CockroachConfig(String configPath) throws IOException, ClassNotFoundException {
        this(PropertiesUtils.load(configPath));
    }

    public CockroachConfig appName(String appName) {
        this.appName = appName;
        return this;
    }

    public CockroachConfig runWithJunit() {
        this.runWithJunit = true;
        return this;
    }

    public boolean isRunWithJunit() {
        return runWithJunit;
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

    public ICHttpClient newHttpClient() throws IllegalAccessException, InstantiationException {
        if (this.httpClientClass != null) {
            return httpClientClass.newInstance();
        }
        return null;
    }

    public IStore newStore() throws IllegalAccessException, InstantiationException {
        if (this.storeClass != null) {
            return this.storeClass.newInstance();
        }
        return null;
    }

    public ICookieGenerator newCookieGenerator() throws IllegalAccessException, InstantiationException {
        if (this.cookieGeneratorClass != null) {
            return this.cookieGeneratorClass.newInstance();
        }
        return null;
    }

    private IHeaderGenerator newHeaderGenerator() throws IllegalAccessException, InstantiationException {
        if (this.headerGeneratorClass != null) {
            return this.headerGeneratorClass.newInstance();
        }
        return null;
    }

    public ICGenerator newProxyGenerator() throws IllegalAccessException, InstantiationException {
        if (this.proxyGeneratorClass != null) {
            return this.proxyGeneratorClass.newInstance();
        }
        return null;
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

    public List<IHeaderGenerator> newHeaderGenerators() throws InstantiationException, IllegalAccessException {
        List<IHeaderGenerator> headerGenerators = new ArrayList<>();
        headerGenerators.add(this.newHeaderGenerator());
        headerGenerators.add(new DefaultHeaderGenerator());
        return headerGenerators.stream().filter(item -> item!=null).collect(Collectors.toList());
    }

    public LogMsgHandler getLogMsgHandler() {
        return this.logMsgHandler;
    }

    public CockroachConfig addLogConsumer(Class<? extends ICMsgConsumer> consumer) {
        try {
            this.logMsgHandler.registerConsumer(consumer.newInstance());
        } catch (InstantiationException|IllegalAccessException e) {
            log.error(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return this;
    }
}
