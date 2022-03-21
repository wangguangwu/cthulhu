package com.wangguangwu.client.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 服务端 demo。
 *
 * @author wangguangwu
 */
public class ServerDemo {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));

        // 监听客户端
        while (true) {
            SocketChannel accept = serverSocketChannel.accept();
            if (accept == null) {
                continue;
            }
        }
    }

}
