package com.wangguangwu.server;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;

/**
 * Parse http request.
 *
 * @author wangguangwu
 */
@Getter
@Setter
public class Request {

    /**
     * request method, such as GET/POST.
     */
    private String method;

    /**
     * request url, such as "/" or "/index.html".
     */
    private String url;

    /**
     * inputStream.
     * <p>
     * get information by parse the inputStream.
     */
    private InputStream inputStream;

    /**
     * inputStream constructor.
     *
     * @param inputStream socketInputStream
     */
    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;

        // 从输入流中获取请求信息
        int count = 0;
        while (count == 0) {
            // 获取输入流中的字节数组大小
            count = inputStream.available();
        }
        // 创建一个缓冲数组去读取对应的字节数据
        byte[] bytes = new byte[count];
        // 没读到结尾
        if (inputStream.read(bytes) != -1) {
            System.out.println("======请求信息如下======\r\n" + new String(bytes));
            System.out.println("======请求信息结束======");
        }
        // 拿到请求信息
        String inputStr = new String(bytes);
        // 获取请求行信息（根据 "\r\n"）
        String requestLine = inputStr.split("\r\n")[0];
        // 根据 " " 切分请求行
        String[] strings = requestLine.split(" ");

        this.method = strings[0];
        this.url = strings[1];

        System.out.println("======method======\r\n" + method);
        System.out.println("======url======\r\n" + url);
    }
}
