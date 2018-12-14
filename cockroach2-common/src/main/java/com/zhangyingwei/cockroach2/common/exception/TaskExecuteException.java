package com.zhangyingwei.cockroach2.common.exception;

import java.io.IOException;

/**
 * @author zhangyw
 * @date: 2018/12/14
 * @desc:
 */
public class TaskExecuteException extends CockroachException {
    public TaskExecuteException() {
    }

    public TaskExecuteException(String message) {
        super(message);
    }

    public TaskExecuteException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskExecuteException(Throwable cause) {
        super(cause);
    }

    public TaskExecuteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
