package com.zhangyingwei.cockroach2.core.http;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.common.exception.TaskExecuteException;
import com.zhangyingwei.cockroach2.http.okhttp.COkHttpClient;
import com.zhangyingwei.cockroach2.session.request.CockroachRequest;
import com.zhangyingwei.cockroach2.session.response.CockroachResponse;

import java.util.HashMap;
import java.util.Map;

public class CockroachHttpClientTest {
    public static void main(String[] args) throws TaskExecuteException {
        CockroachHttpClient client = new CockroachHttpClient(
                new COkHttpClient(),(task) -> {
            return "zhangyingwei".concat(task.getUrl());
        },(task) -> {
            Map map = new HashMap();
            map.put("cockroach", "hello cockroach".concat(task.getGroup()));
            return map;
        });
        CockroachResponse resp = client.execute(new CockroachRequest(new Task("http://zhangyingwei.com")));
        System.out.println(resp.getContent().string());
    }
}