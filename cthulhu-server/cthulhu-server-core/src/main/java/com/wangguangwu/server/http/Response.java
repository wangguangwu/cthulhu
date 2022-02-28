package com.wangguangwu.server.http;

import com.wangguangwu.server.util.HttpProtocolUtil;
import com.wangguangwu.server.util.StaticResourceUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Generate response.
 *
 * @author wangguangwu
 */
public class Response implements HttpServletResponse {

    /**
     * socketOutputStream.
     */
    private final OutputStream outputStream;

    private final Map<String, String> headers = new ConcurrentHashMap<>();

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * output content.
     *
     * @param content content
     * @throws IOException IOException
     */
    public void output(String content) throws IOException {
        // write http response to socketOutputStream
        outputStream.write(content.getBytes());
    }

    /**
     * get static resources according to url and write it to socketOutputStream.
     *
     * @param url url
     */
    public void outputHtml(String url) throws IOException {
        // get the absolute path of the file of the static resource to the url
        String absoluteResourcePath = StaticResourceUtil.getAbsolutePath(url);

        File file = new File(absoluteResourcePath);
        if ((file.exists()) && (file.isFile())) {
            // if fill exists, outputStaticResource
            StaticResourceUtil.outputStaticResource(file, outputStream);
        } else {
            // output 404's response
            output(HttpProtocolUtil.getHttp404());
        }
    }


    @Override
    public void addCookie(Cookie cookie) {

    }

    @Override
    public boolean containsHeader(String name) {
        return false;
    }

    @Override
    public String encodeURL(String url) {
        return null;
    }

    @Override
    public String encodeRedirectURL(String url) {
        return null;
    }

    @Override
    public String encodeUrl(String url) {
        return null;
    }

    @Override
    public String encodeRedirectUrl(String url) {
        return null;
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {

    }

    @Override
    public void sendError(int sc) throws IOException {

    }

    @Override
    public void sendRedirect(String location) throws IOException {

    }

    @Override
    public void setDateHeader(String name, long date) {

    }

    @Override
    public void addDateHeader(String name, long date) {

    }

    @Override
    public void setHeader(String name, String value) {

    }

    @Override
    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    @Override
    public void setIntHeader(String name, int value) {

    }

    @Override
    public void addIntHeader(String name, int value) {

    }

    @Override
    public void setStatus(int sc) {

    }

    @Override
    public void setStatus(int sc, String sm) {

    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public String getHeader(String name) {
        return headers.get(name);
    }

    @Override
    public Collection<String> getHeaders(String name) {
        return null;
    }

    @Override
    public Collection<String> getHeaderNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new CthulhuServletOutputStream(outputStream);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return null;
    }

    @Override
    public void setCharacterEncoding(String charset) {

    }

    @Override
    public void setContentLength(int len) {

    }

    @Override
    public void setContentLengthLong(long len) {

    }

    @Override
    public void setContentType(String type) {

    }

    @Override
    public void setBufferSize(int size) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale loc) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}

