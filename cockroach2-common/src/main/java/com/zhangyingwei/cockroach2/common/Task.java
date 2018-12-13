package com.zhangyingwei.cockroach2.common;

import com.zhangyingwei.cockroach2.common.enmus.RequestType;
import com.zhangyingwei.cockroach2.common.exception.CockroachUrlNotValidException;
import com.zhangyingwei.cockroach2.common.utils.IdUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */

@EqualsAndHashCode
@Slf4j
public class Task implements Comparable<Task> {
    private Long id = IdUtils.getId(Task.class.getName());
    @Setter @Getter
    private String group = Constants.TASK_GROUP_DEFAULT;
    @Getter @Setter
    private String url;
    @Getter
    @Setter
    private Map<String, String> params = new HashMap<>();
    @Setter
    private Object data;
    @Getter
    private RequestType requestType = RequestType.GET;


    /**
     * 任务重试次数
     */
    private Integer retry = Constants.TASK_RETRY;
    /**
     * 任务优先级
     */
    @Setter @Getter
    private Integer priority = Constants.TASK_PRIORITY;

    public Task(String url) {
        this.url = url;
    }

    public Task(String url, String group) {
        this.group = group;
        this.url = url;
    }

    public <T> T getResources() {
        return (T) data;
    }

    public Task group(String group) {
        this.group = group;
        return this;
    }

    public Task retry(Integer num) {
        this.retry = num;
        return this;
    }

    public Task tryOne() {
        this.retry -= 1;
        if (this.retry < 0) {
            log.info("Task({}) retry is over",this.id);
            return null;
        }
        return this;
    }

    /**
     * 基于父 task 生成比之更高一级的优先级
     * @param fatherTask
     * @return
     */
    public Task higherPriorityBy(Task fatherTask) {
        this.priority = fatherTask.getPriority() + 1;
        return this;
    }

    /**
     * 基于父 task 生成比之更低一级的优先级
     * @param fatherTask
     * @return
     */
    public Task lowerPriorityBy(Task fatherTask) {
        this.priority = fatherTask.getPriority() + 1;
        return this;
    }


    @Override
    public int compareTo(Task other) {
        int priorityCompare = other.getPriority() - this.getPriority();
        if (priorityCompare != 0) {
            return priorityCompare;
        }
        return (int)(other.id - this.id);
    }

    public Task doWith(RequestType type) {
        this.requestType = type;
        return this;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id:" + id +
                ", group:'" + group + '\'' +
                ", url:'" + url + '\'' +
                ", params:" + params +
                ", requestType:" + requestType +
                '}';
    }
}
