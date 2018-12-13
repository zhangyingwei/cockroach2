package com.zhangyingwei.cockroach2.core.executor;

import com.zhangyingwei.cockroach2.common.Task;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
public interface ICTaskExecutor {
    Task execute();
    void stop();
}
