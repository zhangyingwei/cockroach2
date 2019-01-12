package com.zhangyingwei.cockroach2.monitor.http.server.session;


import lombok.Data;
import lombok.Getter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class Response {
    @Getter
    private Header header = new Header();
    private Request request;

    @Getter
    private PrintWriter writer;

    public Response(Socket socket, Request request) throws IOException {
        this.request = request;
        this.writer = new PrintWriter(socket.getOutputStream());
    }

    public Response resourcesOk() {
        this.writer.println("HTTP/1.1 200 OK");
        this.writer.println(String.format("%s %s", HeaderKey.STATUS.getKey(), 200));
        this.wtireDefaultHeaders();
        this.writer.println();
        return this;
    }

    private void wtireDefaultHeaders() {
//        this.writer.println(String.format("%s %s", HeaderKey.DATE.getKey(), new Date().toString()));
//        this.writer.println(String.format("%s %s", HeaderKey.CONNECTION.getKey(), "Keep-Alive"));
//        this.writer.println(String.format("%s %s", HeaderKey.CONTENT_ENCODING.getKey(), "gzip"));
//        this.writer.println(String.format("%s %s", HeaderKey.CONTENT_TYPE.getKey(), this.header.getContentType()));
    }

    public Response resourcesNotFound() {
        this.writer.println("HTTP/1.1 400");
        this.writer.println(String.format("%s %s", HeaderKey.STATUS.getKey(), 400));
        this.writer.println();
        this.writer.println("request not found...");
        return this;
    }

    enum HeaderKey {
        DATE("Date:"),
        CONNECTION("Connection:"),
        CONTENT_ENCODING("Content-Encoding:"),
        CONTENT_TYPE("Content-Type:"),
        STATUS("Status:");

        @Getter
        private final String key;
        HeaderKey(String key) {
            this.key = key;
        }
    }


    @Data
    public class Header {
        private String date;
        private String connection;
        private String contentEncoding;
        private String contentType;
        private String status;
    }
}