package com.zhangyingwei.cockroach2.core.http;

import com.zhangyingwei.cockroach2.common.exception.TaskExecuteException;
import com.zhangyingwei.cockroach2.common.generators.ICMapGenerator;
import com.zhangyingwei.cockroach2.common.generators.ICStringGenerator;
import com.zhangyingwei.cockroach2.http.ICHttpClient;
import com.zhangyingwei.cockroach2.http.proxy.ProxyInfo;
import com.zhangyingwei.cockroach2.session.request.CockroachRequest;
import com.zhangyingwei.cockroach2.session.response.CockroachResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */

@Slf4j
public class CockroachHttpClient implements ICHttpClient {
    @NonNull
    private ICHttpClient client;
    private ICStringGenerator cookieGenerator;
    private ICMapGenerator headerGenerator;

    public CockroachHttpClient(@NonNull ICHttpClient client, ICStringGenerator cookieGenerator, ICMapGenerator headerGenerator) {
        this.client = client;
        this.cookieGenerator = cookieGenerator;
        this.headerGenerator = headerGenerator;
    }

    @Override
    public CockroachResponse execute(CockroachRequest request) throws TaskExecuteException {
        if (this.cookieGenerator != null) {
            request.getHeader().setCookie(this.cookieGenerator.generate(request.getTask()));
        }
        if (this.headerGenerator != null) {
            request.getHeader().setHeaders(this.headerGenerator.generate(request.getTask()));
        }
        log.debug("http execute with header: {} ",request.getHeader().getHeaders());
        CockroachResponse response = this.client.execute(request);
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