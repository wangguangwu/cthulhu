package com.wangguangwu.util;

/**
 * @author wangguangwu
 * @date 2022/2/5 1:58 AM
 * @description http 协议工具类，主要是提供响应头信息，这里我们只提供 200 和 404 的响应头信息
 */
public class HttpProtocolUtil {

    /**
     * 为响应码为 200 的响应提供响应头信息
     *
     * @param contentLength 响应内容长度
     * @return string
     */
    public static String getHttp200(long contentLength) {
        return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + contentLength + "\r\n" +
                "\r\n";
    }

    /**
     * 为响应码为 404 的响应提供响应头信息
     *
     * @return string
     */
    public static String getHttp404() {
        String str404 = "<h1>404 Not Found</h1>";
        return "HTTP/1.1 404 Not Found\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + str404.getBytes().length + "\r\n" +
                "\r\n" + str404;
    }

}
