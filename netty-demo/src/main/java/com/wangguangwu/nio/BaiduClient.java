package com.wangguangwu.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * use NIO to access baidu
 *
 * @author wangguangwu
 */
@Slf4j
public class BaiduClient {

    private static final String HOST_NAME = "www.baidu.com";

    private static final int PORT = 80;

    public static void main(String[] args) throws IOException, InterruptedException {

        // we need connect server success, then write message to buffer
        try (SocketChannel socketChannel = SocketChannel.open()) {
            // set up unblocking
            socketChannel.configureBlocking(false);

            // connect baidu
            socketChannel.connect(new InetSocketAddress(HOST_NAME, PORT));

            // if the pattern is non-block, we need to check whether the connection id ready
            // the connection operation must later be completed by invoking the {@link #finishConnect finishConnect} method.
            while (!socketChannel.finishConnect()) {
                Thread.yield();
            }

            // http request
            String requestMsg = """
                    GET / HTTP/1.1\r
                    Host:www.baidu.com\r
                    \r
                    """;

            // write data to buffer
            ByteBuffer requestBuf = ByteBuffer.wrap(requestMsg.getBytes());
            Thread.sleep(5000L);
            socketChannel.write(requestBuf);

            // handle message from server
            ByteBuffer responseBuf = ByteBuffer.allocate(1024);

            int read = socketChannel.read(responseBuf);
            while (read != -1) {
                responseBuf.flip();
                while (responseBuf.hasRemaining()) {
                    byte[] resContext = new byte[responseBuf.limit()];
                    responseBuf.get(resContext);
                    String resStr = new String(resContext);
                    String[] split = resStr.split("\r\n");
                    for (String s : split) {
                        log.info(s);
                    }
                }
                responseBuf.clear();
                read = socketChannel.read(responseBuf);
            }
        }
    }

}

