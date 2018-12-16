package com.zhangyingwei.cockroach2.queue;


import com.zhangyingwei.cockroach2.common.Task;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
public interface ICQueue {
    Task get();
    void add(Task task);
    int size();
    boolean isEmpty();
}
