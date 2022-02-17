package com.wangguangwu.server;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wangguangwu
 * @date 2022/2/5 10:48 PM
 * @description tomcat 启动类
 */
@Getter
@Setter
public class BootStrap {

    private int port = 8080;

    /**
     * tomcat 的程序启动入口
     *
     * @param args args
     */
    public static void main(String[] args) {
        BootStrap bootStrap = new BootStrap();
        try {
            bootStrap.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * tomcat 启动需要初始化展开的一些操作
     * 完成 tomcat 2.0 版本
     * 需求：封装 Request 和 Response 对象，返回 html 静态资源文件
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void start() throws IOException {
        // 创建一个服务端 socket
        ServerSocket serverSocket =
                new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        System.out.println("======>>tomcat start on port " + port);

        while (true) {
            // 调用 accept 方法获取 socket 实例
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();

            // 封装 Request 对象和 Response 对象
            Request request = new Request(inputStream);
            // google 的请求
            if ("/favicon.ico".equals(request.getUrl())) {
                continue;
            }
            Response response = new Response(socket.getOutputStream());

            response.outputHtml(request.getUrl());
            socket.close();
        }

    }

}
