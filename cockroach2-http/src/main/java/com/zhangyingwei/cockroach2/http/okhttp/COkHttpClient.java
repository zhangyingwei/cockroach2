package com.zhangyingwei.cockroach2.http.okhttp;

import com.zhangyingwei.cockroach2.common.enmus.ProxyType;
import com.zhangyingwei.cockroach2.common.exception.TaskExecuteException;
import com.zhangyingwei.cockroach2.http.ICHttpClient;
import com.zhangyingwei.cockroach2.http.proxy.ProxyInfo;
import com.zhangyingwei.cockroach2.session.request.CockroachRequest;
import com.zhangyingwei.cockroach2.session.response.CockroachResponse;
import com.zhangyingwei.cockroach2.session.response.CockroachResponseContent;
import com.zhangyingwei.cockroach2.session.response.ResponseHeaders;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
@Slf4j
public class COkHttpClient implements ICHttpClient {

    private OkHttpClient.Builder clientBuilder;

    public COkHttpClient() {
        this.clientBuilder = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS);
    }

    @Override
    public CockroachResponse execute(CockroachRequest request) throws TaskExecuteException {
        Response response = null;
        try {
            switch (request.getRequestType()) {
                case GET:
                    response = this.doGet(request);
                    break;
                case POST:
                    response = this.doPost(request);
                    break;
                default:
                    log.info("request type ({}) was not supported now", request.getRequestType());
                    break;
            }
        } catch (Exception e) {
            throw new TaskExecuteException(e);
        }
        CockroachResponseContent content = null;
        ResponseHeaders header = null;
        Integer code = null;
        Boolean success = false;
        if (response != null) {
            try {
                content = new CockroachResponseContent(response.body().bytes());
            } catch (IOException e) {
                log.error("get response body error: {} ",e.getLocalizedMessage());
            }
            header = new ResponseHeaders(response.headers().toMultimap());
            code = response.code();
            success = true;
        }
        return new CockroachResponse(content, header, code, success);
    }

    @Override
    public ICHttpClient proxy(ProxyInfo proxyInfo) {
        if (proxyInfo != null) {
            Proxy.Type proxyType = Proxy.Type.HTTP;
            if (ProxyType.HTTP.equals(proxyInfo.getProxyType())) {
                proxyType = Proxy.Type.HTTP;
            } else if (ProxyType.SOCKET5.equals(proxyInfo.getProxyType())) {
                proxyType = Proxy.Type.SOCKS;
            }
            this.clientBuilder = this.clientBuilder.proxy(
                    new Proxy(
                            proxyType, new InetSocketAddress(proxyInfo.getIp(), proxyInfo.getPort())
                    )
            );
            log.info("use proxy: {}", proxyInfo);
        }
        return this;
    }

    /**
     * 发送 GET 请求
     * @param cockroachRequest
     * @return
     * @throws IOException
     */
    private Response doGet(CockroachRequest cockroachRequest) {
        Request request = new Request.Builder()
                .url(cockroachRequest.getUrl())
                .headers(Headers.of(cockroachRequest.getHeader().getHeaders()))
                .get().build();
        try {
            return this.clientBuilder.build().newCall(request).execute();
        } catch (IOException e) {
            log.error("http get error: {}", e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * 发送 POST 请求
     * @param cockroachRequest
     * @return
     * @throws IOException
     */
    private Response doPost(CockroachRequest cockroachRequest) {
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                JSONObject.fromObject(cockroachRequest.getParams()).toString()
        );

        Request request = new Request.Builder()
                .url(cockroachRequest.getUrl())
                .headers(Headers.of(cockroachRequest.getHeader().getHeaders()))
                .post(requestBody).build();
        try {
            return this.clientBuilder.build().newCall(request).execute();
        } catch (IOException e) {
            log.info("http post error: {}", e.getLocalizedMessage());
            return null;
        }
    }
}
