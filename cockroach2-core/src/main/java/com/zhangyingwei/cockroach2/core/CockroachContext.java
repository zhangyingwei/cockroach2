package com.zhangyingwei.cockroach2.core;

import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.executor.ExecuterManager;
import com.zhangyingwei.cockroach2.core.queue.QueueHandler;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */

@Slf4j
public class CockroachContext {
    private boolean start = false;
    private ExecuterManager executerManager;

    public CockroachContext(CockroachConfig config) {
        this.executerManager = new ExecuterManager(config);
    }

    public void start(QueueHandler queue) {
        if (!start) {
            log.info("starting...");
            try {
                this.executerManager.start(queue);
                this.start = true;
            } catch (IllegalAccessException | InstantiationException e) {
                log.info("start faild: {}", e.getLocalizedMessage());
                e.printStackTrace();
            }
            log.info("start success");
        } else {
            log.warn("cockroach had already bean started");
        }
    }
}
