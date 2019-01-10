package com.zhangyingwei.cockroach2.core.generator;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.http.params.ICookieGenerator;

/**
 * @author zhangyw
 * @date: 2019/1/10
 * @desc:
 */
public class TestCookieGenerator implements ICookieGenerator {
    @Override
    public String generate(Task task) {
        return "cookie";
    }
}
