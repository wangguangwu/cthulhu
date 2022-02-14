package com.wangguangwu.client.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wangguangwu
 * @date 2022/2/14 6:56 AM
 * @description 流的工具类
 */
public class StreamUtil {

    /**
     * 输入流转化为字节数组
     *
     * @param inputStream 输入流
     * @return 字节数组
     */
    public static byte[] stream2ByteArray(InputStream inputStream) {
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

}
