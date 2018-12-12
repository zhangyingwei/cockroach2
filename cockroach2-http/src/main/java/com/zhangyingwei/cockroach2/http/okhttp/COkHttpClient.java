package com.zhangyingwei.cockroach2.http.okhttp;

import com.zhangyingwei.cockroach2.common.enmus.RequestType;
import com.zhangyingwei.cockroach2.common.exception.CockroachUrlNotValidException;
import com.zhangyingwei.cockroach2.http.ICHttpClient;
import com.zhangyingwei.cockroach2.http.params.HeaderGenerator;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
@Slf4j
public class COkHttpClient implements ICHttpClient {

    private OkHttpClient.Builder clientBuilder;

    public COkHttpClient() {
        this.clientBuilder = new OkHttpClient.Builder();
    }

    @Override
    public CockroachResponse exetute(CockroachRequest request) throws CockroachUrlNotValidException {
        CockroachResponse cockroachResponse = new CockroachResponse(request.getTask());
        Response response = null;
        switch (request.getRequestType()) {
            case GET:
                response = this.doGet(request);
                break;
            case POST:
                response = this.doPost(request);
                break;
            default:
                log.info("request type ({}) was not supported now",request.getRequestType());
                break;
        }
        if (response == null) {
            cockroachResponse.setSuccess(false);
        } else {
            cockroachResponse.setContent(
                    new CockroachResponseContent(response.body().byteStream())
            );
            cockroachResponse.setHeaders(
                    new ResponseHeaders(response.headers().toMultimap())
            );
            cockroachResponse.setCode(
                    response.code()
            );
            cockroachResponse.setSuccess(true);
        }
        return cockroachResponse;
    }

    @Override
    public ICHttpClient proxy(ProxyInfo proxyInfo) {
        Proxy.Type proxyType = Proxy.Type.HTTP;
        if (proxyInfo != null) {
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
                .headers(Headers.of(cockroachRequest.getHeaders().getHeaders()))
                .get().build();
        try {
            return this.clientBuilder.build().newCall(request).execute();
        } catch (IOException e) {
            log.info("http get error: {}", e.getLocalizedMessage());
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
                .headers(Headers.of(cockroachRequest.getHeaders().getHeaders()))
                .post(requestBody).build();
        try {
            return this.clientBuilder.build().newCall(request).execute();
        } catch (IOException e) {
            log.info("http post error: {}", e.getLocalizedMessage());
            return null;
        }
    }
}
