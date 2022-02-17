package com.wangguangwu.server;

import com.wangguangwu.util.HttpProtocolUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wangguangwu
 * @date 2022/2/5 12:50 AM
 * @description tomcat 启动类
 */
@Getter
@Setter
public class Bootstrap {

    /**
     * 定义 socket 监听的端口号
     */
    private int port = 8080;

    /**
     * tomcat 启动需要初始化展开的一些操作
     * <p>
     * 完成 tomcat 1.0 版本
     * 需求：浏览器请求 http://localhost:8080 时，返回一个固定的字符串 "Hello World" 到页面
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void start() throws IOException {
        // 创建服务端 socket，监听本地的 8080 端口，并且将 backlog（存放未处理 socket 连接上限）设置为 1
        ServerSocket serverSocket
                = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        System.out.println("======>>Tomcat start on port: " + port);

        while (true) {
            // 调用 accept 方法获取 socket
            // accept 是一个阻塞方法
            Socket socket = serverSocket.accept();
            // 接收到请求，成功创建 socket 后，获取输出流
            OutputStream outputStream = socket.getOutputStream();
            String data = "<h1>Hello World</h1>";
            // 书写 http 响应
            String responseText =
                    HttpProtocolUtil.getHttp200(data.getBytes().length) + data;
            outputStream.write(responseText.getBytes());
            socket.close();
        }
    }


    /**
     * tomcat 的启动入口
     *
     * @param args args
     */
    public static void main(String[] args) {
        Bootstrap bootStrap = new Bootstrap();
        try {
            // 启动 tomcat
            bootStrap.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

