package com.wangguangwu.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author wangguangwu
 */
@Slf4j
public class NioServer3 {

    public static void main(String[] args) throws IOException {
        // 1. 创建网络服务端 ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        // 2. 构建一个 Selector 选择器，并且将 channel 注册上去
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, serverSocketChannel);

        // 3. 绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));

        while (true) {
            // 不再轮询通道，改用轮询事件的方式
            // select() 方法有阻塞效果，直到有事件通知才会有返回
            selector.select();

            // 获取事件遍历查询结果
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();

                // 关注 accept 和 read 两个事件
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) selectionKey.attachment();

                    // 将拿到的客户端连接通道，注册到 selector 上面
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ, socketChannel);
                    log.info("receive new client connect: {}", socketChannel.getRemoteAddress());
                }

                if (selectionKey.isReadable()) {
                    try {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.attachment();
                        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);

                        while (socketChannel.isOpen()
                                && socketChannel.read(requestBuffer) != -1) {
                            if (requestBuffer.position() > 0) {
                                break;
                            }
                        }

                        // 转化为读取模式
                        requestBuffer.flip();

                        if (requestBuffer.limit() > 0) {
                            // 将 requestBuffer 中的数据读取到字节数组，然后打印出来
                            byte[] content = new byte[requestBuffer.limit()];
                            requestBuffer.get(content);

                            log.info("receive message [{}] from {}", new String(content), socketChannel.getRemoteAddress());

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

                    } catch (IOException e) {
                        selectionKey.cancel();
                    }
                }
            }
            selector.selectNow();
        }
    }

}
