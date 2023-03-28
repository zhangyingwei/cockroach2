package com.zhangyingwei.cockroach2.core.http;

import com.zhangyingwei.cockroach2.common.exception.TaskExecuteException;
import com.zhangyingwei.cockroach2.common.generators.ICMapGenerator;
import com.zhangyingwei.cockroach2.common.generators.ICStringGenerator;
import com.zhangyingwei.cockroach2.common.utils.LogUtils;
import com.zhangyingwei.cockroach2.http.ICHttpClient;
import com.zhangyingwei.cockroach2.http.params.ICookieGenerator;
import com.zhangyingwei.cockroach2.http.params.IHeaderGenerator;
import com.zhangyingwei.cockroach2.http.proxy.ProxyInfo;
import com.zhangyingwei.cockroach2.session.request.CockroachRequest;
import com.zhangyingwei.cockroach2.session.response.CockroachResponse;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */

@Slf4j
public class CockroachHttpClient implements ICHttpClient {
    @NonNull@Getter
    private ICHttpClient client;
    private ICookieGenerator cookieGenerator;
    private List<IHeaderGenerator> headerGenerators;

    public CockroachHttpClient(@NonNull ICHttpClient client, ICookieGenerator cookieGenerator, List<IHeaderGenerator> headerGenerators) {
        this.client = client;
        this.cookieGenerator = cookieGenerator;
        this.headerGenerators = headerGenerators;
    }

    @Override
    public CockroachResponse execute(CockroachRequest request) throws TaskExecuteException {
        if (this.cookieGenerator != null) {
            request.getHeader().setCookie(this.cookieGenerator.generate(request.getTask()));
        }
        if (this.headerGenerators!=null && this.headerGenerators.size() > 0) {
            this.headerGenerators.forEach(headerGenerator -> {
                request.getHeader().setHeaders(headerGenerator.generate(request.getTask()));
            });
        }
        CockroachResponse response = this.client.execute(request);
        log.debug("{} : {}\t header: {}", LogUtils.getTagColor(request.getRequestType()), request.getUrl(), request.getHeader().getHeaders());
        if (response != null) {
            response.setTask(request.getTask());
        }
        return response;
    }

    @Override
    public ICHttpClient proxy(ProxyInfo proxyInfo) {
        if (proxyInfo!=null && proxyInfo.valid()) {
            this.client.proxy(proxyInfo);
        } else {
            log.debug("proxy: {} not valid, connect without proxy", proxyInfo);
        }
        return this;
    }
}