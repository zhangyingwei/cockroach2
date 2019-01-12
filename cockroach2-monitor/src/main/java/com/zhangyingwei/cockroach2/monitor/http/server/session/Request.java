package com.zhangyingwei.cockroach2.monitor.http.server.session;

import com.zhangyingwei.cockroach2.monitor.http.server.exception.MethodNotMatchException;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@ToString
@Slf4j
public class Request {
    private final Socket socket;
    @Getter
    private Method method;
    @Getter
    private String path;
    private String httpVersion;

    @Getter
    private Header header = new Header();

    public Request(Socket currentSocket) throws IOException, MethodNotMatchException {
        this.socket = currentSocket;
        this.init();
    }

    private void init() throws IOException, MethodNotMatchException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = reader.readLine();
        int lineNum = 0;
        while (line != null && !line.isEmpty()) {
            this.readLine(++lineNum, line);
            line = reader.readLine();
        }
    }

    private void readLine(int lineNum, String line) throws MethodNotMatchException {
//        log.debug("line: {} : {}",lineNum,line);
        if (lineNum == 1) {
            if (line.startsWith("GET")) {
                this.method = Method.GET;
            } else if (line.startsWith("POST")) {
                this.method = Method.POST;
            } else {
                throw new MethodNotMatchException(line);
            }
            String[] items = line.split(" ");
            this.path = items[1];
            this.httpVersion = items[2];
        }
        if (line.startsWith(HeaderKey.HOST.getKey())) {
            this.header.setHost(line);
        } else if (line.startsWith(HeaderKey.CONNECTION.getKey())) {
            this.header.setConnection(line);
        } else if (line.startsWith(HeaderKey.CACHE_CONTROL.getKey())) {
            this.header.setCacheControl(line);
        } else if (line.startsWith(HeaderKey.USER_AGENT.getKey())) {
            this.header.setUserAgent(line);
        } else if (line.startsWith(HeaderKey.ACCEPT.getKey())) {
            this.header.setAccept(line);
        } else if (line.startsWith(HeaderKey.ACCEPT_ENCODING.getKey())) {
            this.header.setAcceptEncoding(line);
        } else if (line.startsWith(HeaderKey.ACCEPT_LANGUAGE.getKey())) {
            this.header.setAcceptLanguage(line);
        } else if (line.startsWith(HeaderKey.COOKIE.getKey())) {
            this.header.setCookie(line);
        }
    }

    enum Method {
        GET,POST
    }

    enum HeaderKey {
        HOST("Host:"),
        CONNECTION("Connection:"),
        CACHE_CONTROL("Cache-Control:"),
        USER_AGENT("User-Agent:"),
        ACCEPT("Accept:"),
        ACCEPT_ENCODING("Accept-Encoding:"),
        ACCEPT_LANGUAGE("Accept-Language:"),
        COOKIE("Cookie:");

        @Getter
        private final String key;
        HeaderKey(String key) {
            this.key = key;
        }
    }

    @ToString
    public class Header {
        private String host;
        private String connection;
        private String cacheControl;
        private String userAgent;
        private String accept;
        private String acceptEncoding;
        private String acceptLanguage;
        private String cookie;

        private String getValue(String line, HeaderKey key) {
            return line.replaceFirst(key.getKey(), "").trim();
        }

        public String getHost() {
            return host;
        }

        public void setHost(String line) {
            this.host = this.getValue(line,HeaderKey.HOST);
        }

        public String getConnection() {
            return connection;
        }

        public void setConnection(String connection) {
            this.connection = this.getValue(connection,HeaderKey.CONNECTION);
        }

        public String getCacheControl() {
            return cacheControl;
        }

        public void setCacheControl(String cacheControl) {
            this.cacheControl = this.getValue(cacheControl, HeaderKey.CACHE_CONTROL);
        }

        public String getUserAgent() {
            return userAgent;
        }

        public void setUserAgent(String userAgent) {
            this.userAgent = this.getValue(userAgent, HeaderKey.USER_AGENT);
        }

        public String getAccept() {
            return accept;
        }

        public void setAccept(String accept) {
            this.accept = this.getValue(accept, HeaderKey.ACCEPT);
        }

        public String getAcceptEncoding() {
            return acceptEncoding;
        }

        public void setAcceptEncoding(String acceptEncoding) {
            this.acceptEncoding = this.getValue(acceptEncoding, HeaderKey.ACCEPT_ENCODING);
        }

        public String getAcceptLanguage() {
            return acceptLanguage;
        }

        public void setAcceptLanguage(String acceptLanguage) {
            this.acceptLanguage = this.getValue(acceptLanguage, HeaderKey.ACCEPT_LANGUAGE);
        }

        public String getCookie() {
            return cookie;
        }

        public void setCookie(String cookie) {
            this.cookie = this.getValue(cookie, HeaderKey.COOKIE);
        }
    }
}