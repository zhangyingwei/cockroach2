package com.zhangyingwei.cockroach2.core.http;

import com.zhangyingwei.cockroach2.common.exception.CockroachUrlNotValidException;
import com.zhangyingwei.cockroach2.common.exception.TaskExecuteException;
import com.zhangyingwei.cockroach2.http.ICHttpClient;
import com.zhangyingwei.cockroach2.http.proxy.ProxyInfo;
import com.zhangyingwei.cockroach2.session.request.CockroachRequest;
import com.zhangyingwei.cockroach2.session.response.CockroachResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */

@RequiredArgsConstructor
@Slf4j
public class CockroachHttpClient implements ICHttpClient {
    @NonNull
    private ICHttpClient client;

    @Override
    public CockroachResponse exetute(CockroachRequest request) throws TaskExecuteException {
        CockroachResponse response = this.client.exetute(request);
        if (response != null) {
            response.setTask(request.getTask());
        }
        return response;
    }

    @Override
    public ICHttpClient proxy(ProxyInfo proxyInfo) {
        this.client.proxy(proxyInfo);
        return this;
    }
}