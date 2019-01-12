package com.zhangyingwei.cockroach2.monitor.http.server.exception;


public class MethodNotMatchException extends Exception {
    public MethodNotMatchException() {
        super();
    }

    public MethodNotMatchException(String message) {
        super(message);
    }

    public MethodNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodNotMatchException(Throwable cause) {
        super(cause);
    }

    protected MethodNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
