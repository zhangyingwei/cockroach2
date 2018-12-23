package com.zhangyingwei.cockroach2.monitor.model;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.common.enmus.RequestType;
import com.zhangyingwei.cockroach2.common.enmus.TaskStatu;
import lombok.Data;
import java.util.Map;

/**
 * task 监控信息对象
 */
@Data
public class TaskModel implements Comparable<TaskModel> {
    private Long id;
    private String group;
    private String url;
    private Map<String, String> params;
    private Object data;
    private RequestType requestType;
    private TaskStatu statu;
    private Integer priority;
    private Long startTimestamp;

    public TaskModel(Task task) {
        this.id = task.getId();
        this.group = task.getGroup();
        this.data = task.getData();
        this.params = task.getParams();
        this.priority = task.getPriority();
        this.requestType = task.getRequestType();
        this.statu = task.getStatu();
        this.url = task.getUrl();
    }

    @Override
    public int compareTo(TaskModel out) {
        return id.compareTo(out.id);
    }
}
