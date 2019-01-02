package com.zhangyingwei.cockroach2.core.listener;

import com.zhangyingwei.cockroach2.monitor.mvc.CockroachMonitorApplication;
import com.zhangyingwei.cockroach2.monitor.storage.StorageHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyw
 * @date: 2019/1/2
 * @desc:
 */

@Slf4j
public class ApplicationListener implements ICListener {
    public void onStart() {
        log.info("executors start!");
        System.out.println(System.getProperties());
        CockroachMonitorApplication.start();
    }

    public void onStop() {
        log.info("executors done!");
        CockroachMonitorApplication.stop();
    }
}
