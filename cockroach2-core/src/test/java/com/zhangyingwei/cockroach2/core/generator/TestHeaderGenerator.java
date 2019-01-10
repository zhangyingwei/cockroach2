package com.zhangyingwei.cockroach2.core.generator;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.http.params.IHeaderGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyw
 * @date: 2019/1/10
 * @desc:
 */
public class TestHeaderGenerator implements IHeaderGenerator {
    @Override
    public Map generate(Task task) {
        return new HashMap();
    }
}
