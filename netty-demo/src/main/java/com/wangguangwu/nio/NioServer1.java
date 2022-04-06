package com.wangguangwu.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * nio server
 *
 * @author wangguangwu
 */
@Slf4j
public class NioServer1 {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));
        log.info("server start success");

        while (true) {
            // 监听新 tcp 连接通道
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel == null) {
                continue;
            }
            log.info("receive a new client connection");
            // 设为阻塞模式
            // TODO: question
            socketChannel.configureBlocking(true);

            // 接收数据
            // TODO：请求字节长度大于 1024 怎么解决
            ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
            while (socketChannel.isOpen()
                    && socketChannel.read(requestBuffer) != -1) {
                if (requestBuffer.position() > 0) {
                    break;
                }
            }

            // 转为读取模式
            requestBuffer.flip();

            // 将 requestBuffer 中的数据读取到字节数组，然后打印出来
            byte[] content = new byte[requestBuffer.limit()];
            requestBuffer.get(content);

            log.info("request: {}", new String(content));
            log.info("receive message from: {}", socketChannel.getRemoteAddress());

            // 响应客户端
            String response = """
                    HTTP/1.1 200 OK\r
                    Content-Length: 11\r
                    \r
                    Hello World""";
            ByteBuffer responseBuffer = ByteBuffer.wrap(response.getBytes());
            while (responseBuffer.hasRemaining()) {
                socketChannel.write(responseBuffer);
            }
        }
    }

}
