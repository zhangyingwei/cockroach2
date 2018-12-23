package com.zhangyingwei.cockroach2.monitor.model;

import lombok.Data;

/**
 * executor 信息对象
 */
@Data
public class ExecutorModel implements Comparable<ExecutorModel> {
    private String name;
    private Boolean keepRun = true;
    private Class clientClass;
    private Class proxyClass;
    private Class storeClass;
    private int threadSleep;
    private Long startTimestamp;
    private Long successNum;
    private Long failedNum;

    @Override
    public int compareTo(ExecutorModel out) {
        return this.name.compareTo(out.name);
    }
}