package com.zhangyingwei.cockroach2.monitor.http.server.action;

import com.zhangyingwei.cockroach2.db.CockroachDb;
import com.zhangyingwei.cockroach2.monitor.http.server.session.Request;
import com.zhangyingwei.cockroach2.monitor.http.server.session.Response;
import com.zhangyingwei.cockroach2.monitor.msg.Msg;
import net.sf.json.JSONObject;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class TaskInfoAction implements ICAction {
    private final CockroachDb db;

    public TaskInfoAction(CockroachDb cockroachDb) {
        this.db = cockroachDb;
    }

    @Override
    public String doAction(Request request, Response response) {
        response.getHeader().setContentType("application/json");
        Map resultData = new HashMap();
        resultData.put("taskcount", db.getAcc("taskCount"));
        resultData.put("taskrunning", db.getAcc("taskRunning"));
        resultData.put("tasksuccess", db.getAcc("taskSuccess"));
        resultData.put("taskfailed", db.getAcc("taskFailed"));
        Map<String,Integer> taskGropu = new HashMap<String, Integer>();
        List<Msg> taskList = this.db.getList("tasklist");
        for (Msg msg : taskList) {
            Integer count = taskGropu.getOrDefault(msg.getMsgOf(Msg.Keys.TASK_GROUP), 0);
            taskGropu.put(msg.getMsgOf(Msg.Keys.TASK_GROUP)+"", ++count);
        }
        resultData.put("taskgroup", taskGropu);
        List<Object> taskUrls = taskList.stream().collect(Collectors.toList());
        Collections.reverse(taskUrls);
        resultData.put("taskurls", taskUrls);
        return JSONObject.fromObject(resultData).toString();
    }
}
