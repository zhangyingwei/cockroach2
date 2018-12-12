package com.zhangyingwei.cockroach2.core.http;

import com.zhangyingwei.cockroach2.common.exception.CockroachUrlNotValidException;
import com.zhangyingwei.cockroach2.http.ICHttpClient;
import com.zhangyingwei.cockroach2.http.proxy.ProxyInfo;
import com.zhangyingwei.cockroach2.session.request.CockroachRequest;
import com.zhangyingwei.cockroach2.session.response.CockroachResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */

@RequiredArgsConstructor
public class CockroachHttpClient implements ICHttpClient {
    @NonNull
    private ICHttpClient client;

    @Override
    public CockroachResponse exetute(CockroachRequest request) throws IOException, CockroachUrlNotValidException {
        return this.client.exetute(request);
    }

    @Override
    public ICHttpClient proxy(ProxyInfo proxyInfo) {
        return this.client.proxy(proxyInfo);
    }
}
