package com.zhangyingwei.cockroach2.common.async;

import com.zhangyingwei.cockroach2.common.Constants;
import com.zhangyingwei.cockroach2.common.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
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
        CompletableFuture<Void> future = CompletableFuture.runAsync(asyncExecutor::executeVoid, executor);
    }

    public static void shutdown() throws InterruptedException {
        executor.shutdown();
    }

    public static boolean isTerminated() {
        return executor.isTerminated();
    }
}