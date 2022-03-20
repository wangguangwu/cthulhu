package com.wangguangwu.client.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author wangguangwu
 */
public class Server {

    public static void main(String[] args) throws IOException {
        // create serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // binding port
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // set not blocking
        serverSocketChannel.configureBlocking(false);
        // get selector
        try (Selector selector = Selector.open()) {
            // register serverSocketChannel to selector
            // set status OP_ACCEPT
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            // selector.select() > 0, represent get an event
            while (selector.select() > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    // get an event
                    SelectionKey next = iterator.next();
                    // if status is OP_ACCEPT
                    // represent a new client is connecting
                    if (next.isAcceptable()) {
                        // return a socketChannel to client
                        SocketChannel accept = serverSocketChannel.accept();
                        accept.configureBlocking(false);
                        // register current socketChannel to selector
                        // just focus on OP_READ, and set a buffer
                        accept.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                        System.out.println("Accept a client connecting...");
                    } else if (next.isReadable()) {
                        // if the status is OP_READ
                        // get the channel corresponding to the key
                        SocketChannel channel = (SocketChannel) next.channel();
                        // get the linked buffer
                        ByteBuffer buffer = (ByteBuffer) next.attachment();
                        while (channel.read(buffer) != -1) {
                            buffer.flip();
                            System.out.println(new String(buffer.array(), 0, buffer.limit()));
                            buffer.clear();
                        }
                    }
                    iterator.remove();
                }
            }
        }
    }

}
