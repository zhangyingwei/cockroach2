package com.zhangyingwei.cockroach2.common;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
public class Constants {
    public static final String TASK_GROUP_DEFAULT = "default";
    public static final Integer TASK_RETRY = 1;
    public static final Integer TASK_PRIORITY = 0;
    public static final int QUEUE_SIZE = 1024;
    public static final Boolean QUEUE_BLOCK = false;
    /**
     * 异步方法调用，线程池大小
     */
    public static final int ASYN_METHOD_THREAD_NUM = 100;

    public static final String THREAD_NAME_EXECUTER = "Executor-";
    public static final String THREAD_NAME_MOINTOR = "Monitor-";
    public static final String THREAD_NAME_EXECUTER_TMP = "Executor@tmp-";
    public static final String THREAD_NAME_MAIN = "Main";
}
