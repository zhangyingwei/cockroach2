package com.zhangyingwei.cockroach2.common.generators;

import com.zhangyingwei.cockroach2.common.Task;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */
public interface ICGenerator<T> {
    T generate(Task task);
}
