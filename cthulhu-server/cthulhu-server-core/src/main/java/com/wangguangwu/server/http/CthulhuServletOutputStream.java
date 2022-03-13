package com.wangguangwu.server.http;

import com.wangguangwu.server.util.HttpProtocolUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author wangguangwu
 */
public class CthulhuServletOutputStream extends ServletOutputStream {

    private final OutputStream outputStream;

    public CthulhuServletOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {

    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        outputStream.write(HttpProtocolUtil.getHttp200(len).getBytes(StandardCharsets.UTF_8));
        outputStream.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        outputStream.flush();
    }

    @Override
    public void close() throws IOException {
        outputStream.close();
    }

    @Override
    public void write(byte[] b) throws IOException {
        outputStream.write(b);
    }

    @Override
    @SuppressWarnings("all")
    public void write(int b) throws IOException {

    }
}
