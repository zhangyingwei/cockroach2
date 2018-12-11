package com.zhangyingwei.cockroach2.core.executor;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
@Slf4j
public class TaskTest {
    @Before
    public void before() {}
    @Test
    public void getResources() {
        Task task = new Task("url", "group");
        Map data = new HashMap<String, String>() {
            {
                put("key", "value");
            }
        };
        task.setData(data);
        Map data2 = task.getResources();
        log.info("hello log");
        Assert.assertEquals(data, data2);
    }

    @Test
    public void testGetName() {
        log.info(Task.class.getName());
    }
}