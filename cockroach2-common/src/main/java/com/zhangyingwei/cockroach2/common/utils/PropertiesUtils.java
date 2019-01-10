package com.zhangyingwei.cockroach2.common.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author zhangyw
 * @date: 2019/1/9
 * @desc:
 */
public class PropertiesUtils {
    public static String getOrDefault(Properties properties, String key, Object defaultValue) {
        return (String) properties.getOrDefault(key, defaultValue);
    }

    public static Properties load(String path) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(path));
        return properties;
    }
}
