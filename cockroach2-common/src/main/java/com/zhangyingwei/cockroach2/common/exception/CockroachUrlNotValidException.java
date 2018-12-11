package com.zhangyingwei.cockroach2.common.exception;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
public class CockroachUrlNotValidException extends Exception {
    public CockroachUrlNotValidException() {
        super();
    }

    public CockroachUrlNotValidException(String message) {
        super(message);
    }

    public CockroachUrlNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public CockroachUrlNotValidException(Throwable cause) {
        super(cause);
    }

    protected CockroachUrlNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
