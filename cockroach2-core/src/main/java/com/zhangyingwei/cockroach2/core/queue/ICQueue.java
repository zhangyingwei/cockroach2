package com.zhangyingwei.cockroach2.core.queue;

import com.zhangyingwei.cockroach2.core.executor.Task;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
public interface ICQueue {
    Task get(Boolean withBlock);
    void add(Task task);
}
