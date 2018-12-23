package com.zhangyingwei.cockroach2.monitor.model;

import lombok.Data;

/**
 * 队列监控信息对象
 */
@Data
public class QueueModel {
    private Class queueClass;
    private Boolean withBlock;
    private Long limit;
    private Long size;
    private Long outSize;
    private Long calacify;
    private Long createTimestemp;
}
