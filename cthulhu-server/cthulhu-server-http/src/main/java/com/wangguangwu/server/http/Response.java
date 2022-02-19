package com.wangguangwu.server.http;

import com.wangguangwu.util.HttpProtocolUtil;
import com.wangguangwu.util.StaticResourceUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

/**
 * Generate response.
 *
 * @author wangguangwu
 */
@NoArgsConstructor
@AllArgsConstructor
public class Response implements ServletResponse {

    /**
     * socketOutputStream.
     */
    private OutputStream outputStream;

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
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
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

