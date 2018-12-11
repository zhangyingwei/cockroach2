package com.zhangyingwei.cockroach2.core.queue.filter;

import com.zhangyingwei.cockroach2.core.executor.Task;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
public interface ICQueueFilter {
    boolean accept(Task task);
}
