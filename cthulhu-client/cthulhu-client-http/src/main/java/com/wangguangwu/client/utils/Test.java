package com.wangguangwu.client.utils;

import java.net.Socket;

/**
 * @author wangguangwu
 */
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.InetAddress;

public class Test {
    public static void main(String[] args) {
        try {
            Test test = new Test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Test() throws Exception {
        InetAddress inetAddress = InetAddress.getByName("www.baidu.com");
        Socket socket = new Socket(inetAddress.getHostAddress(), 80);

        if (socket.isConnected()) {
            System.out.println("连接建立,远程地址:" + socket.getRemoteSocketAddress());
        }

        // 关键！此处在Socket的输出流写入HTTP的GET报文，请服务器做出响应。
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write("GET / HTTP/1.1\r\n");
        bw.write("Host: www.baidu.com\r\n");
        bw.write("\r\n");
        bw.flush();

        // 开始读取远程服务器的响应数据。
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());

        byte[] buffer = new byte[1024];
        int count = 0;
        while (true) {
            count = bis.read(buffer);
            if (count == -1) {
                break;
            }

            System.out.println(new String(buffer, 0, count, "UTF-8"));
        }

        bw.close();
        bis.close();
        socket.close();
    }
}
