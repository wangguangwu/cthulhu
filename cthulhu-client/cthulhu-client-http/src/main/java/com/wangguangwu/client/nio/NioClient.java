package com.wangguangwu.client.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author wangguangwu
 */
@Slf4j
public class NioClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));

        // 判断是否连接上，没有连接上则一直等待
        while (!socketChannel.finishConnect()) {
            Thread.yield();
        }

        Scanner scanner = new Scanner(System.in);
        log.info("请输入要发送的内容");
        // 发送内容
        String next = scanner.nextLine();
        ByteBuffer requestBuffer = ByteBuffer.wrap(next.getBytes());
        while (requestBuffer.hasRemaining()) {
            socketChannel.write(requestBuffer);
        }

        // 读取响应
        log.info("收到服务端响应");
        ByteBuffer responseBuffer = ByteBuffer.allocate(1024);
        while (socketChannel.isOpen()
                && socketChannel.read(responseBuffer) != -1) {
            if (responseBuffer.position() > 0) {
                break;
            }
        }

        // 转化为读取模式
        responseBuffer.flip();

        // 将 responseBuffer 中的数据读取到字节数组，然后打印出来
        byte[] content = new byte[responseBuffer.limit()];
        responseBuffer.get(content);
        // TODO: 逻辑考虑一下
        log.info(new String(content));
    }

}
