package com.zhangyingwei.cockroach2.core.http;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.common.enmus.RequestType;
import com.zhangyingwei.cockroach2.common.exception.TaskExecuteException;
import com.zhangyingwei.cockroach2.http.okhttp.COkHttpClient;
import com.zhangyingwei.cockroach2.session.request.CockroachRequest;
import com.zhangyingwei.cockroach2.session.response.CockroachResponse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CockroachHttpClientTest {
    @Test
    public void execute() throws TaskExecuteException {
        CockroachHttpClient client = new CockroachHttpClient(new COkHttpClient(), null, null);
        Task task = new Task("http://blog.zhangyingwei.com/api/admin/login");
        task.setParams(new HashMap<String, String>(){
            {
                put("username", "admin");
                put("password", "123");
            }
        });
        task.doWith(RequestType.POST);
        CockroachResponse resp = client.execute(new CockroachRequest(task));
        System.out.println(resp.getContent().string());
    }

    @Test
    public void execute1() {
        //TODO
    }

    @Test
    public void proxy() {
        //TODO
    }

    @Test
    public void getClient() {
        //TODO
    }
}