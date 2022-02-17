package com.wangguangwu.server;

import com.wangguangwu.util.HttpProtocolUtil;
import com.wangguangwu.util.StaticResourceUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author wangguangwu
 * @date 2022/2/5 11:59 PM
 * @description 将响应信息封装为 Response 对象（依赖于 OutputStream）
 * <p>
 * 该对象需要提供核心方法，输出静态资源（html）
 */
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private OutputStream outputStream;

    /**
     * 使用输出流输出指定字符串
     *
     * @param content 字符串内容
     * @throws IOException IOException
     */
    public void output(String content) throws IOException {
        outputStream.write(content.getBytes());
    }

    /**
     * 根据 url 获取到静态资源的绝对路径，并根据绝对路径读取该静态资源，最终通过输出流输出
     *
     * @param path url
     */
    public void outputHtml(String path) throws IOException {
        // 获取静态资源的文件的绝对路径
        String absoluteResourcePath = StaticResourceUtil.getAbsolutePath(path);

        // 输入静态资源文件
        File file = new File(absoluteResourcePath);
        if ((file.exists()) && (file.isFile())) {
            // 读取静态资源文件，输出静态资源
            StaticResourceUtil.outputStaticResource(new FileInputStream(file), outputStream);
        } else {
            // 输出 404
            output(HttpProtocolUtil.getHttp404());
        }
    }
}

