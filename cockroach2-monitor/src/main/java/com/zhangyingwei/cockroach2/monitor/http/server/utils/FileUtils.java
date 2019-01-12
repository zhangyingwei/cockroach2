package com.zhangyingwei.cockroach2.monitor.http.server.utils;

import java.io.*;
import java.util.stream.Collectors;

public class FileUtils {

    public static String getContent(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String result = reader.lines().collect(Collectors.joining());
            return result;
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean exits(String staticPath) {
        return new File(staticPath).exists();
    }
}
