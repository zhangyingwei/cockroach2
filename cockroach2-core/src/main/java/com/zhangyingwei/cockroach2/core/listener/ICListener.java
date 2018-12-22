package com.zhangyingwei.cockroach2.core.listener;

public interface ICListener<T> {
    void before(T t);
    void after(T t);
}
