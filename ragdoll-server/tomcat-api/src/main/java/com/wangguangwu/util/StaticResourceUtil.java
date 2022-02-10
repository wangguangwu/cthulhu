package com.wangguangwu.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @author wangguangwu
 * @date 2022/2/5 11:26 PM
 * @description 静态资源工具类
 */
public class StaticResourceUtil {

    /**
     * 获取静态资源文件的绝对路径
     *
     * @param path 相对路径
     * @return 静态资源文件的绝对路径
     */
    public static String getAbsolutePath(String path) {
        String absolutePath =
                Objects.requireNonNull(StaticResourceUtil.class.getResource("/")).getPath();
        return absolutePath.replaceAll("\\\\", "/") + path;
    }

    /**
     * 读取静态资源文件输入流，通过输出流输出
     *
     * @param inputStream  静态资源文件输入流
     * @param outputStream 输出流
     */
    public static void outputStaticResource(InputStream inputStream, OutputStream outputStream) throws IOException {

        int count = 0;
        while (count == 0) {
            // 输入流字节数目
            count = inputStream.available();
        }

        int resourceSize = count;
        // 输出 http 请求头
        outputStream.write(HttpProtocolUtil.getHttp200(resourceSize).getBytes());

        // 读取内容输出
        // 已经读取的内容长度
        long written = 0L;
        // 缓冲数组的长度
        int byteSize = 1024;
        byte[] bytes = new byte[byteSize];

        while (written < resourceSize) {
            // 说明剩余未读取大小不足一个 1024 长度，那就按真实长度处理
            if (written + byteSize > resourceSize) {
                // 剩余的文件长度
                byteSize = (int) (resourceSize - written);
                bytes = new byte[byteSize];
            }
            // 没有读到结尾
            if (inputStream.read(bytes) != -1) {
                outputStream.write(bytes);
                outputStream.flush();
            }
            written += byteSize;
        }
    }

}
