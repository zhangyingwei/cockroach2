package com.zhangyingwei.cockroach2.common.async;

import com.zhangyingwei.cockroach2.common.Constants;
import com.zhangyingwei.cockroach2.common.utils.IdUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class AsyncUtils {

    private static ExecutorService executor = Executors.newFixedThreadPool(Constants.ASYN_METHOD_THREAD_NUM, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("async-"+IdUtils.getId("async"));
            return thread;
        }
    });

    public static void doVoidMethodAsync(AsyncVoidExecutor asyncExecutor) {
        CompletableFuture.runAsync(asyncExecutor::executeVoid,executor);
    }

    public static void shutdown() {
        executor.shutdown();
    }
}