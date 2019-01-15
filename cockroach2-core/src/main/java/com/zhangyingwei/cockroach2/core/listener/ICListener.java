package com.zhangyingwei.cockroach2.core.listener;

public interface ICListener<T> {
    void action(T msg);
    ListenerType type();

    enum ListenerType {
        APPLICATION_START, APPLICATION_STOP,
        EXECUTOR_START, EXECUTOR_RUNNING, EXECUTOR_END,
        TASK_EXECUTE, TASK_STORE, TASK_FINISH, TASK_FAILD, TASK_AFTER, TASK_BEFORE,
    }
}