package com.zhangyingwei.cockroach2.request.http.okhttp;

import com.zhangyingwei.cockroach2.request.CockroachRequest;
import com.zhangyingwei.cockroach2.request.http.ICHttpClient;
import com.zhangyingwei.cockroach2.response.CockroachResponse;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
public class OkHttpClient implements ICHttpClient {
    @Override
    public CockroachResponse exetute(CockroachRequest request) {
        CockroachResponse response = new CockroachResponse();
        return response;
    }
}
