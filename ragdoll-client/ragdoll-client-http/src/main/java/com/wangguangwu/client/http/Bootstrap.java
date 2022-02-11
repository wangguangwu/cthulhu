package com.wangguangwu.client.http;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.Socket;

/**
 * @author wangguangwu
 * @date 2022/2/11 4:04 PM
 * @description 使用 socket 发送 http 请求
 */
@Getter
@Setter
public class Bootstrap {

    private int port = 8080;

    private String host = "127.0.0.1";

    public static void main(String[] args) {
        try {
            Bootstrap bootstrap = new Bootstrap();
            // 发送 http 请求
            bootstrap.sendHttp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendHttp() throws IOException {
        // 创建一个 socket 往本地的 80 端口发送请求
        Socket socket = new Socket(host, port);
        // 获取输出流和输入流
        OutputStream out = socket.getOutputStream();
        InputStream input = socket.getInputStream();

        StringBuffer httpRequest = new StringBuffer();
        // 请求行、请求头、请求空行、请求体
        httpRequest.append("GET /index.html HTTP/1.1\r\n");
        httpRequest.append("Connection: keep-alive\r\n");
        httpRequest.append("\r\n");
        // 往输入流中写入请求
        out.write(httpRequest.toString().getBytes());
        // 必须要刷新
        out.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String response = "";
        System.out.println("response:\r\n");
        // 输出响应
        while ((response = reader.readLine()) != null) {
            System.out.println(response);
        }

        // 确保主动断开连接的时候不会阻塞在 in.read() 方法中，需要先执行 socket.shutdownInput() 方法
        socket.shutdownInput();
        input.close();
        out.close();
        socket.close();
    }
}
