package com.zhangyingwei.cockroach2.common.exception;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
public class CockroachException extends Exception {
    public CockroachException() {
    }

    public CockroachException(String message) {
        super(message);
    }

    public CockroachException(String message, Throwable cause) {
        super(message, cause);
    }

    public CockroachException(Throwable cause) {
        super(cause);
    }

    public CockroachException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
