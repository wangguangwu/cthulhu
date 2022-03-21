package com.wangguangwu.client.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 客户端 demo.
 *
 * @author wangguangwu
 */
public class ClientDemo {

    public static void main(String[] args) throws IOException {
        try (SocketChannel socketChannel = SocketChannel.open()) {
            // 默认状态下是阻塞模式，调用 api 修改为非阻塞模式
            socketChannel.configureBlocking(false);
            // 连接服务器
            socketChannel.connect(new InetSocketAddress(("127.0.0.1"), 8080));

            // 发送请求数据-向通道写入数据
            ByteBuffer buffer = ByteBuffer.allocate(1);
            socketChannel.write(buffer);

            // 读取服务端返回-读取缓冲区的数据
            int read = socketChannel.read(buffer);
        }
    }

}
