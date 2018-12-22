package com.zhangyingwei.cockroach2.http.params;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.session.request.RequestHeader;

import java.util.HashMap;
import java.util.Map;

public class DefaultHeaderGenerator implements IHeaderGenerator {
    private Map<String, String> headers = new HashMap<>();

    private final static String ACCEPT_VALUE = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8";
    private final static String ACCEPT_ENCODING_VALUE = "gzip, deflate, br";
    private final static String ACCEPT_LANGUAGE_VALUE = "zh-CN,zh;q=0.9";
    private final static String CACHE_CONTROL_VALUE = "max-age=0";
    private final static String CONNECTION_VALUE = "keep-alive";
    private final static String COOKIE_VALUE = "";
    private final static String USER_AGENT_VALUE = "";
    @Override
    public Map generate(Task task) {
        //        this.headers.put(ACCEPT_KEY, ACCEPT_VALUE);
//        this.headers.put(ACCEPT_ENCODING_KEY, ACCEPT_ENCODING_VALUE);
        this.headers.put(RequestHeader.ACCEPT_LANGUAGE_KEY, ACCEPT_LANGUAGE_VALUE);
        this.headers.put(RequestHeader.CACHE_CONTROL_KEY, CACHE_CONTROL_VALUE);
        this.headers.put(RequestHeader.CONNECTION_KEY, CONNECTION_VALUE);
        this.headers.put(RequestHeader.COOKIE_KEY, COOKIE_VALUE);
        this.headers.put(RequestHeader.USER_AGENT_KEY, USER_AGENT_VALUE);
        return headers;
    }
}
