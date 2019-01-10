package com.zhangyingwei.cockroach2.common.utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author zhangyw
 * @date: 2019/1/10
 * @desc:
 */
public class ThreadSleepToolTest {

    @Test
    public void getSleepTimePrint() {
        ThreadSleepTool sleepTool = new ThreadSleepTool(50000,3000);
        for (int i = 0; i < 10; i++) {
            System.out.println(sleepTool.getSleepTime());
        }
    }

    @Test
    public void getSleepTime() {
        ThreadSleepTool sleepTool = new ThreadSleepTool(5000,500);
        for (int i = 0; i < 1000000; i++) {
            Assert.assertTrue(sleepTool.getSleepTime() < 5000);
            Assert.assertTrue(sleepTool.getSleepTime() >= 500);
        }
    }
}