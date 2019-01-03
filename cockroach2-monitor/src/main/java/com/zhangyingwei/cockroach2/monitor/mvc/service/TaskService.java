package com.zhangyingwei.cockroach2.monitor.mvc.service;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.monitor.model.QueueModel;
import com.zhangyingwei.cockroach2.monitor.model.TaskModel;
import com.zhangyingwei.cockroach2.monitor.storage.StorageHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author zhangyw
 * @date: 2019/1/3
 * @desc:
 */
public class TaskService {

    public Map getQueryInfo() {
        Map<String, Object> info = new HashMap<>();
        List<TaskModel> tasks = StorageHandler.listAllTasks();
        info.put("count", tasks.size());
        info.put("run", tasks.stream().filter(taskModel ->
                Task.Statu.EXECUTE.equals(taskModel.getStatu())
                        || Task.Statu.START.equals(taskModel.getStatu())
                        ||Task.Statu.VALID.equals(taskModel.getStatu()) || Task.Statu.STORE.equals(taskModel.getStatu())
        ).count());
        info.put("finish", tasks.stream().filter(taskModel -> Task.Statu.FINISH.equals(taskModel.getStatu())).count());
        info.put("faild", tasks.stream().filter(taskModel -> Task.Statu.VALID.equals(taskModel.getStatu())).count());
        return info;
    }
}
