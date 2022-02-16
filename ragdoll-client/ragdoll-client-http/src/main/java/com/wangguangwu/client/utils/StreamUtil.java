package com.wangguangwu.client.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author wangguangwu
 * @date 2022/2/14 6:56 AM
 * @description 流的工具类
 */
@Slf4j
public class StreamUtil {

    /**
     * 输入流转化为字节数组
     *
     * @param inputStream 输入流
     * @return 字节数组
     */
    public static byte[] stream2ByteArray(InputStream inputStream, int readLength, int responseBodyLength) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int i;
        try {
            while ((i = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toByteArray();
    }

    public static byte[] readFirst(InputStream inputStream) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        try {
            inputStream.read(buffer);
            output.write(buffer, 0, 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toByteArray();
    }

    public static void main(String[] args) {
        String path = Objects.requireNonNull(StreamUtil.class.getResource("/")).getPath();
        System.out.println(path);
    }

    public static byte[] parseInputStream2bytes(InputStream inputStream, int capacity) {

        byte[] buffer = new byte[capacity];

        int readCount = 0;
        int oldReadCount;
        try {
            while (readCount < capacity) {
                oldReadCount = readCount;
                readCount += inputStream.read(buffer, readCount, capacity - readCount);
                // -1
                if (readCount < oldReadCount) {
                    break;
                }
            }
        } catch (IOException e) {
            log.error("parse error: \r\n", e);
        }
        return buffer;
    }
}
