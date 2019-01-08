package com.zhangyingwei.cockroach2.monitor.msg;

import com.zhangyingwei.cockroach2.common.Constants;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 谁 什么时间 做了什么事
 */
public class Msg {
    private final String uuid = UUID.randomUUID().toString();
    /**
     * 消息分组
     */
    @Getter
    private Group group;
    @Getter
    private Long timestamp = System.currentTimeMillis();
    @Getter
    private String name;
    @Getter
    private Map<String,Object> msgMap;

    public Msg(String name,Group group) {
        this.name = name;
        this.group = group;
        this.msgMap = new ConcurrentHashMap<String, Object>();
    }

    public Msg(String name, Map<String, Object> msgMap) {
        this.name = name;
        this.msgMap.putAll(msgMap);
    }

    public Msg addMsg(Keys key,Object msg) {
        this.msgMap.put(key.getValue(), msg);
        return this;
    }

    public Object getMsgOf(Keys key) {
        return this.msgMap.get(key.getValue());
    }

    public enum Keys {
        APPLICATION_ACTION("application.action"),
        APPLICATION_NAME("application.name"),
        APPLICATION_NUM_THREAD("application.thread.num"),

        TASK_STATE("task.state"),
        TASK_URL("task.url"),
        TASK_GROUP("task.group"),
        TASK_REQ_TYPE("task.req.type"),
        TASK_REQ_PARAMS("task.req.params"),
        TASK_ACTION("task.action"),

        EXECUTOR_ACTION("executor.action"),
        EXECUTOR_TIMESTAMP("executor.timestamp");

        @Getter
        private final String value;
        Keys(String value) {
            this.value = value;
        }
    }

    public enum Group {
        APPLICATION,TASK,EXECUTOR
    }
}