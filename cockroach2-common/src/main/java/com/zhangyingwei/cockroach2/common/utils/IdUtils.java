package com.zhangyingwei.cockroach2.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
public class IdUtils {
    private static Map<String, Long> idMap = new ConcurrentHashMap<>();

    public static Long getId(String name) {
        Long id = idMap.getOrDefault(name, -1L) + 1;
        idMap.put(name, id);
        return id;
    }
}
