package com.zhangyingwei.cockroach2.common.utils;

import org.fusesource.jansi.Ansi;

/**
 * @author zhangyw
 * @date: 2019/1/9
 * @desc:
 */
public class LogUtils {
    public static String getTagColor(Object log) {
        return Ansi.ansi().bg(Ansi.Color.YELLOW).fg(Ansi.Color.BLACK).a(log).reset().toString();
    }
    public static String getLineColot(String line) {
        return Ansi.ansi().fg(Ansi.Color.GREEN).a(line).reset().toString();
    }

    public static Object getQueueTagColor(String name) {
        return Ansi.ansi().bg(Ansi.Color.GREEN).fg(Ansi.Color.BLACK).a(name).reset().toString();
    }

    public static Object getExecutorTagColor(String name) {
        return Ansi.ansi().bg(Ansi.Color.CYAN).fg(Ansi.Color.BLACK).a(name).reset().toString();
    }
}
