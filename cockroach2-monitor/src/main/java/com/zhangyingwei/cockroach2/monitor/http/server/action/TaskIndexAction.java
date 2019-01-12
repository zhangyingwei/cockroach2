package com.zhangyingwei.cockroach2.monitor.http.server.action;

import com.zhangyingwei.cockroach2.db.CockroachDb;
import com.zhangyingwei.cockroach2.monitor.http.server.session.Request;
import com.zhangyingwei.cockroach2.monitor.http.server.session.Response;

public class TaskIndexAction implements ICAction {
    private final CockroachDb db;

    public TaskIndexAction(CockroachDb cockroachDb) {
        this.db = cockroachDb;
    }

    @Override
    public String doAction(Request request, Response response) {
        response.getHeader().setContentType("text/html");
        return "task.html";
    }
}
