package com.zhangyingwei.cockroach2.session.response;

import cn.wanghaomiao.xpath.model.JXDocument;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.ByteArrayOutputStream;
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
    private InputStream inputStream;
    private byte[] bytes;
    @Getter
    private String charset;
    private Document document;
    private JXDocument xdocument;
    private JSONObject jsonObject;
    private JSONArray jsonArray;

    public String string() {
        try {
            if (this.charset != null) {
                return new String(this.bytes(), this.charset);
            } else {
                return new String(this.bytes());
            }
        } catch (Exception e) {
            log.info("to string error: {}", e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public byte[] bytes() throws IOException {
        if (this.bytes == null || this.bytes.length == 0) {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int length = this.inputStream.read(bytes);
            while (length > 0) {
                byteStream.write(bytes, 0, length);
                length = this.inputStream.read(bytes);
            }
            this.bytes = byteStream.toByteArray();
        }
        return this.bytes;
    }

    /**
     * 输入流，主要用于下载数据
     * @return
     */
    public InputStream stream() {
        return this.inputStream;
    }

    /**
     * 设置编码
     *
     * @param charset
     * @return
     */
    public CockroachResponseContent charset(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * to Jsoup document
     *
     * @return
     */
    public Document toDocument() {
        if (this.document == null) {
            this.document = Jsoup.parse(Optional.ofNullable(this.string()).orElse(""));
        }
        return this.document;
    }

    /**
     * to xpath document
     *
     * @return
     */
    public JXDocument toXDocument() {
        if (this.xdocument == null) {
            this.xdocument = new JXDocument(this.toDocument());
        }
        return this.xdocument;
    }

    /**
     * to json object
     *
     * @return
     */
    public JSONObject toJsobOject() {
        if (this.jsonObject == null) {
            this.jsonObject = JSONObject.fromObject(Optional.ofNullable(this.string()).orElse(""));
        }
        return this.jsonObject;
    }

    /**
     * to json array
     *
     * @return
     */
    public JSONArray toJsonArray() {
        if (this.jsonArray == null) {
            this.jsonArray = JSONArray.fromObject(Optional.ofNullable(this.string()).orElse(""));
        }
        return this.jsonArray;
    }

}
