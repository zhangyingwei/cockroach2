package com.zhangyingwei.cockroach2.common.async;

import com.zhangyingwei.cockroach2.common.Constants;
import com.zhangyingwei.cockroach2.common.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class AsyncUtils {

    /**
     * 使用 newSingleThreadExecutor 线程次
     * 其主要特征为： 使用唯一的线程来工作，从而保证多有的任务都是 FIFO
     */
    private static ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName(Constants.THREAD_NAME_ASYNC + IdUtils.getId("async"));
            return thread;
        }
    });

    public static void doVoidMethodAsync(AsyncVoidExecutor asyncExecutor) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(asyncExecutor::executeVoid, executor);
    }

    public static void shutdown() throws InterruptedException {
        executor.shutdown();
        log.debug("async shutdown!");
    }

    public static boolean isTerminated() {
        return executor.isTerminated();
    }
}