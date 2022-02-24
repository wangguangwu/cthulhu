package com.wangguangwu.server.http;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * parse http request.
 *
 * @author wangguangwu
 */
@Slf4j
public class Request implements ServletRequest {

    /**
     * request method, such as GET or POST.
     */
    private String method;

    /**
     * request url, such as "/" or "/index.html".
     */
    private String url;

    /**
     * inputStream.
     * <p>
     * get information from socketInputStream.
     */
    private InputStream inputStream;

    /**
     * inputStream constructor.
     *
     * @param inputStream socketInputStream
     */
    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;

        // parse inputStream
        // buffered array
        byte[] bytes = new byte[1024];
        // read length
        int len;
        StringBuilder request = new StringBuilder();
        // -1: represent the end of socketInputStream
        if ((len = inputStream.read(bytes)) != -1) {
            request.append(new String(bytes, 0, len, StandardCharsets.UTF_8));
        }
        log.info("request: \r\n{}", request);

        // requestLine, such as GET / HTTP/1.1
        String requestLine = request.toString().split("\r\n")[0];
        String[] strings = requestLine.split(SPACE);
        this.method = strings[0];
        this.url = strings[1];
        log.info("requestLine: {}", requestLine);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    @Override
    public Enumeration getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {

    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getParameter(String name) {
        return null;
    }

    @Override
    public Enumeration getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String name) {
        return new String[0];
    }

    @Override
    public Map getParameterMap() {
        return null;
    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String name, Object o) {

    }

    @Override
    public void removeAttribute(String name) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }

    @Override
    public String getRealPath(String path) {
        return null;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }
}
