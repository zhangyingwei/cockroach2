package com.zhangyingwei.cockroach2.session.response;

import cn.wanghaomiao.xpath.model.JXDocument;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */
@RequiredArgsConstructor
@Slf4j
public class CockroachResponseContent {
    @Getter
    @Setter
    @NonNull
    private byte[] bytes;
    @Getter
    private String charset;
    private Document document;
    private JXDocument xdocument;

    public String string() {
        try {
            if (this.charset != null) {
                return new String(this.bytes, this.charset);
            } else {
                return new String(this.bytes);
            }
        } catch (UnsupportedEncodingException e) {
            log.info("to string error: {}", e.getLocalizedMessage());
        }
        return "";
    }

    public byte[] bytes() throws IOException {
        return bytes;
    }

    /**
     * 设置编码
     * @param charset
     * @return
     */
    public CockroachResponseContent charset(String charset) {
        this.charset = charset;
        return this;
    }

    public Document toDocument() {
        if (this.document == null) {
            this.document = Jsoup.parse(Optional.ofNullable(this.string()).orElse(""));
        }
        return this.document;
    }

    public JXDocument toXDocument() {
        if (this.xdocument == null) {
            this.xdocument = new JXDocument(this.toDocument());
        }
        return this.xdocument;
    }

}
