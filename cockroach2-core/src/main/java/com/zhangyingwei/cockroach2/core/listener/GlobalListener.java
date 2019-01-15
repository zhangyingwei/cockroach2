package com.zhangyingwei.cockroach2.core.listener;

import com.zhangyingwei.cockroach2.core.config.CockroachConfig;
import com.zhangyingwei.cockroach2.core.listener.impls.application.ApplicationStartListener;
import com.zhangyingwei.cockroach2.core.listener.impls.application.ApplicationStopListener;
import com.zhangyingwei.cockroach2.core.listener.impls.executor.ExecutorEndListener;
import com.zhangyingwei.cockroach2.core.listener.impls.executor.ExecutorRunningListener;
import com.zhangyingwei.cockroach2.core.listener.impls.executor.ExecutorStartListener;
import com.zhangyingwei.cockroach2.core.listener.impls.task.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyw
 * @date: 2019/1/15
 * @desc:
 */
public class GlobalListener {
    private List<ICListener> listeners = new ArrayList<ICListener>();;
    private CockroachConfig config;

    public GlobalListener(CockroachConfig config) {
        this.config = config;
        this.initListeners();
    }

    public GlobalListener() {
        this.initListeners();
    }

    private void initListeners() {
        //TODO
        this.listeners.add(new ApplicationStartListener(config));
        this.listeners.add(new ApplicationStopListener(config));
        this.listeners.add(new ExecutorStartListener(config));
        this.listeners.add(new ExecutorRunningListener(config));
        this.listeners.add(new ExecutorEndListener(config));
        this.listeners.add(new TaskBeforeListener(config));
        this.listeners.add(new TaskExecuteListener(config));
        this.listeners.add(new TaskFaildListener(config));
        this.listeners.add(new TaskFinishListener(config));
        this.listeners.add(new TaskStoreListener(config));
    }

    public void action(ICListener.ListenerType type, Object msg) {
        listeners.parallelStream().filter(listener -> listener.type() != null).filter(listener -> type.equals(listener.type())).forEach(listener -> listener.action(msg));
    }
}
