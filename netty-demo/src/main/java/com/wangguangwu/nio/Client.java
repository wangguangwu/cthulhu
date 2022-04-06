package com.wangguangwu.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author wangguangwu
 */
@Slf4j
public class Client {

    public static void main(String[] args) throws IOException {

        try (// get a socketChannel
             SocketChannel socketChannel = SocketChannel.open()) {
            // set as no-blocking
            socketChannel.configureBlocking(false);
            InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
            // connect the server
            if (!socketChannel.connect(inetSocketAddress)) {
                while (!socketChannel.finishConnect()) {
                    log.info("Connect need time, have dinner first...");
                }
            }
            // connect success, send data
            String str = "Hello World";
            ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
            socketChannel.write(buffer);
            log.info("Client exit...");
        }
    }

}
