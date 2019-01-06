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
    public static final int MSG_QUEUE_SIZE = 1024;
    public static final Boolean QUEUE_BLOCK = false;
    /**
     * 异步方法调用，线程池大小
     */
    public static final int ASYN_METHOD_THREAD_NUM = 100;

    public static final String THREAD_NAME_EXECUTER = "executor-";
    public static final String THREAD_NAME_MOINTOR = "monitor-";
    public static final String THREAD_NAME_MOINTOR_CONSUMER = "log-";
    public static final String THREAD_NAME_EXECUTER_TMP = "executor@tmp-";
    public static final String THREAD_NAME_ASYNC = "async-";
    public static final String THREAD_NAME_MAIN = "main";

    public static final String MSG_DEFAULT_GROUP = "default";
}
