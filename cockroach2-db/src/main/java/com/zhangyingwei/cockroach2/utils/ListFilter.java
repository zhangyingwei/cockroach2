package com.zhangyingwei.cockroach2.utils;

/**
 * @author zhangyw
 * @date: 2019/1/11
 * @desc:
 */
public interface ListFilter<T> {
    boolean filter(T t);
}
