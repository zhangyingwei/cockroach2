package com.zhangyingwei.cockroach2.http.params;

import com.zhangyingwei.cockroach2.common.generators.ICMapGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */
public class HeaderGenerator implements ICMapGenerator {
    @Override
    public Map<String,String> generator() {
        return new HashMap<String,String>();
    }
}
