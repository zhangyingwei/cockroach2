package com.zhangyingwei.cockroach2.session.request;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
public class RequestHeader {
    @Getter
    private Map<String, String> headers = new HashMap<>();

    private final static String ACCEPT_KEY = "Accept";
    private final static String ACCEPT_ENCODING_KEY = "Accept-Encoding";
    private final static String ACCEPT_LANGUAGE_KEY = "Accept-Language";
    private final static String CACHE_CONTROL_KEY = "Cache-Control";
    private final static String CONNECTION_KEY = "Connection";
    private final static String COOKIE_KEY = "Cookie";
    private final static String USER_AGENT_KEY = "User-Agent";


    private final static String ACCEPT_VALUE = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8";
    private final static String ACCEPT_ENCODING_VALUE = "gzip, deflate, br";
    private final static String ACCEPT_LANGUAGE_VALUE = "zh-CN,zh;q=0.9";
    private final static String CACHE_CONTROL_VALUE = "max-age=0";
    private final static String CONNECTION_VALUE = "keep-alive";
    private final static String COOKIE_VALUE = "";
    private final static String USER_AGENT_VALUE = "";

    public RequestHeader() {
        this.initHeaders();
    }

    private void initHeaders() {
        this.headers.put(ACCEPT_KEY, ACCEPT_VALUE);
//        this.headers.put(ACCEPT_ENCODING_KEY, ACCEPT_ENCODING_VALUE);
        this.headers.put(ACCEPT_LANGUAGE_KEY, ACCEPT_LANGUAGE_VALUE);
        this.headers.put(CACHE_CONTROL_KEY, CACHE_CONTROL_VALUE);
        this.headers.put(CONNECTION_KEY, CONNECTION_VALUE);
        this.headers.put(COOKIE_KEY, COOKIE_VALUE);
        this.headers.put(USER_AGENT_KEY, USER_AGENT_VALUE);
    }

    public void setCookie(String cookie) {
        this.headers.put(COOKIE_KEY, cookie);
    }

    public void setUserAgent(String userAgent) {
        this.headers.put(USER_AGENT_KEY, userAgent);
    }

    public void setHeader(String key,String value) {
        this.headers.put(key, value);
    }

    public void setHeaders(Map<String,String> headers) {
        this.headers.putAll(headers);
    }
}
