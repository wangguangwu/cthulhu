package com.wangguangwu.client.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author wangguangwu
 */
public class Client {

    public static void main(String[] args) throws IOException {
        // get a socketChannel
        SocketChannel socketChannel = SocketChannel.open();
        // set as no-blocking
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        // connect the server
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("Connect need time, have dinner first...");
            }
        }
        // connect success, send data
        String str = "Hello World";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(buffer);
        socketChannel.close();
        System.out.println("Client exit...");
    }

}
