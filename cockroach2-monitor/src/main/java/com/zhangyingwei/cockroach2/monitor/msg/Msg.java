package com.zhangyingwei.cockroach2.monitor.msg;

import com.zhangyingwei.cockroach2.common.Constants;
import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 谁 什么时间 做了什么事
 */
public class Msg {
    /**
     * 消息分组
     */
    @Getter
    private String group = Constants.MSG_DEFAULT_GROUP;
    @Getter
    private Long timestamp = System.currentTimeMillis();
    @Getter
    private String name;
    @Getter
    private Map<String,Object> msgMap;

    public Msg(String name) {
        this.name = name;
        this.msgMap = new ConcurrentHashMap<String, Object>();
    }

    public Msg(String name, Map<String, Object> msgMap) {
        this.name = name;
        this.msgMap = msgMap;
    }

    public Msg addMsg(String key,Object msg) {
        this.msgMap.put(key, msg);
        return this;
    }
}