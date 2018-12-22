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

    public final static String ACCEPT_KEY = "Accept";
    public final static String ACCEPT_ENCODING_KEY = "Accept-Encoding";
    public final static String ACCEPT_LANGUAGE_KEY = "Accept-Language";
    public final static String CACHE_CONTROL_KEY = "Cache-Control";
    public final static String CONNECTION_KEY = "Connection";
    public final static String COOKIE_KEY = "Cookie";
    public final static String USER_AGENT_KEY = "User-Agent";

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
