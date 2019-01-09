package com.zhangyingwei.cockroach2.common;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
public class ConfigConstants {
    public static final String KEY_CONFIG_APPNAME = "app.name";
    public static final String KEY_CONFIG_THREADSLEEP = "app.thread.sleep";
    public static final Integer THREAD_SLEEP = 500;
    public static final String KEY_CONFIG_NUMTHREAD = "app.thread.num";
    public static final Integer THREAD_NUM = 1;
    public static final String KEY_CONFIG_STORE = "app.store";
    public static final String STORE = "com.zhangyingwei.cockroach2.core.store.PrintStore";
    public static final String KEY_CONFIG_PROXYGENERATOR = "app.generator.proxy";
    public static final String KEY_CONFIG_HEADERGENERATOR = "app.generator.header";
    public static final String KEY_CONFIG_HTTPCLIENT = "app.http.client";
    public static final Object HTTPCLIENT = "com.zhangyingwei.cockroach2.http.okhttp.COkHttpClient";
    public static final String KEY_CONFIG_LOGCONSUMERS = "app.log.consumers";
}
