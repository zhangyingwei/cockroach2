package com.zhangyingwei.cockroach2.monitor.http.server.action;

import com.zhangyingwei.cockroach2.db.CockroachDb;
import com.zhangyingwei.cockroach2.monitor.http.server.session.Request;
import com.zhangyingwei.cockroach2.monitor.http.server.session.Response;
import com.zhangyingwei.cockroach2.monitor.msg.Msg;
import net.sf.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

public class ExecutorInfoAction implements ICAction {
    private final CockroachDb db;

    public ExecutorInfoAction(CockroachDb cockroachDb) {
        this.db = cockroachDb;
    }

    @Override
    public String doAction(Request request, Response response) {
        response.getHeader().setContentType("application/json");
        Map resultData = new HashMap();
        resultData.put("executorcount", db.getAcc("executorCount"));
        resultData.put("executorrunning", db.getAcc("executorStart"));
        resultData.put("executorend", db.getAcc("executorEnd"));

        List<Msg> msgList = this.db.getList("executor", new Comparator<Msg>() {
            @Override
            public int compare(Msg o1, Msg o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        Collections.sort(msgList, new Comparator<Msg>() {
            Map<String, Integer> map = new HashMap<String, Integer>(){
                {
                    put("start", -1);
                    put("execute", 0);
                    put("stop", 1);
                }
            };
            @Override
            public int compare(Msg o1, Msg o2) {
                String oldAction = o1.getMsgOf(Msg.Keys.EXECUTOR_ACTION).toString();
                String newAction = o2.getMsgOf(Msg.Keys.EXECUTOR_ACTION).toString();
                return map.get(oldAction) - map.get(newAction) > 0? -1:1;
            }
        });

        resultData.put("executorList", msgList.stream().map(msg -> Arrays.asList(msg.getName(), msg.getMsgOf(Msg.Keys.EXECUTOR_ACTION))).
                collect(Collectors.toList()));

        return JSONObject.fromObject(resultData).toString();
    }
}
