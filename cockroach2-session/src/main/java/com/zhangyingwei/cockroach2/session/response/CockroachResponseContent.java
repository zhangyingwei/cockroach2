package com.zhangyingwei.cockroach2.session.response;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */
@RequiredArgsConstructor
public class CockroachResponseContent {
    @Getter
    @Setter
    @NonNull
    private InputStream inputStream;
    @Getter @Setter
    private String charset = "UTF-8";
    @Getter
    private Document document;

    public String string() throws IOException {
        int length = inputStream.available();
        byte[] bytes = new byte[length];
        return new String(bytes,charset);
    }
}
