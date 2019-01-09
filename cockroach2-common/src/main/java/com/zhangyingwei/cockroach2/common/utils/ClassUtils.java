package com.zhangyingwei.cockroach2.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangyw
 * @date: 2019/1/9
 * @desc:
 */
public class ClassUtils {
    public static <T> T getOrNullClass(String clazz) throws ClassNotFoundException {
        if (clazz == null) {
            return null;
        }
        return (T) Class.forName(clazz);
    }

    public static <T>T getClasses(String classes) {
        if (classes == null) {
            return (T) new ArrayList();
        }
        return (T) Arrays.stream(classes.replaceAll(" ", "")
                .split(",")).map(clazz -> {
            try {
                return Class.forName(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(clazz -> clazz != null).collect(Collectors.toList());
    }
}
