package com.zhangyingwei.cockroach2.common.utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author zhangyw
 * @date: 2019/1/9
 * @desc:
 */
public class NameUtilTest {

    @Test
    public void getName() {
        for (int i = 0; i < 1000000; i++) {
            String name = NameUtil.getName();
            Assert.assertNotNull(name);
        }
    }
}