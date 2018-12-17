package com.zhangyingwei.cockroach2.core;

import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.executor.ExecuterManager;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */

@Slf4j
public class CockroachContext {
    private boolean start = false;
    private ExecuterManager executerManager;
    private CockroachConfig config;

    public CockroachContext(CockroachConfig config) {
        this.executerManager = new ExecuterManager(config);
        this.config = config;
    }

    public void start(QueueHandler queue) {
        if (!start) {
            this.printAppInfo();
            log.info("starting...");
            try {
                this.executerManager.start(queue);
                this.start = true;
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                log.info("start faild: {}", e.getLocalizedMessage());
                e.printStackTrace();
            }
            log.info("start success");
        } else {
            log.warn("cockroach had already bean started");
        }
    }

    private void printAppInfo() {
        this.config.print();
    }
}
