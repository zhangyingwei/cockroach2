package com.zhangyingwei.cockroach2.core.executor;

import com.zhangyingwei.cockroach2.common.Task;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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
        Map data2 = task.getData();
        log.info("hello log");
        Assert.assertEquals(data, data2);
    }

    @Test
    public void testGetName() {
        log.info(Task.class.getName());
    }
}