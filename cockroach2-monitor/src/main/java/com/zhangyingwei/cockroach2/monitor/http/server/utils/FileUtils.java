package com.zhangyingwei.cockroach2.monitor.http.server.utils;

import com.zhangyingwei.cockroach2.monitor.http.server.CockroachHttpServer;

import java.io.*;
import java.net.URL;
import java.util.stream.Collectors;

public class FileUtils {

    public static String getContent(Class<CockroachHttpServer> clazz, URL filePath) throws IOException {
        InputStream inputStream = clazz.getClassLoader().getResourceAsStream(filePath.getPath());
        BufferedReader reader = null;
        try {
            if (inputStream == null) {
                inputStream = filePath.openStream();
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String result = reader.lines().collect(Collectors.joining("\n"));
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

    public static boolean exits(Class<CockroachHttpServer> clazz, URL staticPath) {
        try {
            InputStream res = clazz.getClassLoader().getResourceAsStream(staticPath.getPath());
            if (res == null) {
                staticPath.openStream();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
