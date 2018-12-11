package com.zhangyingwei.cockroach2.request.http;

import com.zhangyingwei.cockroach2.request.CockroachRequest;
import com.zhangyingwei.cockroach2.response.CockroachResponse;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
public interface ICHttpClient {
    CockroachResponse exetute(CockroachRequest request);
}