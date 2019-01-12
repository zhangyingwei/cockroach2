package com.zhangyingwei.cockroach2.monitor.http.server.action;

import com.zhangyingwei.cockroach2.db.CockroachDb;
import com.zhangyingwei.cockroach2.monitor.http.server.session.Request;
import com.zhangyingwei.cockroach2.monitor.http.server.session.Response;

public class MainIndexAction implements ICAction {
    private final CockroachDb db;

    public MainIndexAction(CockroachDb cockroachDb) {
        this.db = cockroachDb;
    }

    @Override
    public String doAction(Request request, Response response) {
        response.getHeader().setContentType("text/html");
        return "index.html";
    }
}
