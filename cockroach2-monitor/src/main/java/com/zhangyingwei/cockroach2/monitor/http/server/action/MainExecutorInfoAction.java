package com.zhangyingwei.cockroach2.monitor.http.server.action;

import com.zhangyingwei.cockroach2.db.CockroachDb;
import com.zhangyingwei.cockroach2.monitor.http.server.session.Request;
import com.zhangyingwei.cockroach2.monitor.http.server.session.Response;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainExecutorInfoAction implements ICAction {
    private final CockroachDb db;

    public MainExecutorInfoAction(CockroachDb cockroachDb) {
        this.db = cockroachDb;
    }

    @Override
    public String doAction(Request request, Response response) {
        response.getHeader().setContentType("application/json");
        Map resultData = new HashMap();
        resultData.put("executorrunning", db.getAcc("executorStart"));
        resultData.put("executorend", db.getAcc("executorEnd"));
        return JSONObject.fromObject(resultData).toString();
    }
}
