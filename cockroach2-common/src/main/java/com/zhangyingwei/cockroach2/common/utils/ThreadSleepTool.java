package com.zhangyingwei.cockroach2.common.utils;

import java.util.Random;

/**
 * @author zhangyw
 * @date: 2019/1/10
 * @desc:
 */
public class ThreadSleepTool {
    private boolean israndom = true;
    private Integer maxSleepTime;
    private Integer minSleepTime = 0;
    private Random random = new Random(1024);

    public ThreadSleepTool(Integer maxSleepTime,boolean israndom) {
        this.maxSleepTime = maxSleepTime;
        this.israndom = israndom;
    }

    public ThreadSleepTool(Integer maxSleepTime, Integer minSleepTime) {
        this.maxSleepTime = maxSleepTime;
        this.minSleepTime = minSleepTime;
    }

    public synchronized Integer getSleepTime() {
        if (israndom) {
            return random.nextInt(maxSleepTime - minSleepTime) + minSleepTime;
        }
        return this.maxSleepTime;
    }
}