package com.zhangyingwei.cockroach2.http;


import com.zhangyingwei.cockroach2.common.exception.CockroachUrlNotValidException;
import com.zhangyingwei.cockroach2.http.proxy.ProxyInfo;
import com.zhangyingwei.cockroach2.session.response.CockroachResponse;
import com.zhangyingwei.cockroach2.session.request.CockroachRequest;

import java.io.IOException;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
public interface ICHttpClient {
    CockroachResponse exetute(CockroachRequest request);
    ICHttpClient proxy(ProxyInfo proxyInfo);
}