package com.wangguangwu.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author wangguangwu
 */
@Slf4j
public class NioServer2 {

    private static List<SocketChannel> socketChannels = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));
        log.info("server start success");

        while (true) {
            // 监听新的 tcp 连接通道
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                log.info("receive a new request from {}", socketChannel.getRemoteAddress());
                // 设置为非阻塞模式
                socketChannel.configureBlocking(false);
                // 加入通道集合
                socketChannels.add(socketChannel);
            } else {
                // 在没有新通知进来的时候
                // 遍历通道集合，处理现有连接的数据，处理完之后就删除
                Iterator<SocketChannel> iterator = socketChannels.iterator();
                while (iterator.hasNext()) {
                    SocketChannel channel = iterator.next();
                    // 接收数据-判断当前连接是否有数据需要读取
                    // 如果没有数据，就处理下一个连接
                    ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
                    if (channel.read(requestBuffer) == 0) {
                        continue;
                    }

                    while (channel.isOpen()
                            && channel.read(requestBuffer) != -1) {
                        if (requestBuffer.position() > 0) {
                            break;
                        }
                    }

                    // 转为读取模式
                    requestBuffer.flip();

                    // 将 requestBuffer 中的数据读取到字节数组，然后打印出来
                    byte[] content = new byte[requestBuffer.limit()];
                    requestBuffer.get(content);

                    log.info("receive message [{}] from {}", new String(content), channel.getRemoteAddress());

                    // 响应客户端
                    String response = """
                            HTTP/1.1 200 OK\r
                            Content-Length: 11\r
                            \r
                            Hello World""";

                    ByteBuffer responseBuffer = ByteBuffer.wrap(response.getBytes());
                    while (responseBuffer.hasRemaining()) {
                        channel.write(responseBuffer);
                    }
                    iterator.remove();
                }
            }
        }
    }

}
