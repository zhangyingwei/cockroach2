package com.zhangyingwei.cockroach2.core.executor;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
public interface ICTaskExecutor {
    void execute();
    void stop();
}
