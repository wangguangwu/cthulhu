package com.wangguangwu.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author wangguangwu
 */
@Slf4j
public class GroupChatServer {

    /**
     * selector
     */
    private Selector selector;

    /**
     * listenChannel
     */
    private ServerSocketChannel listenChannel;

    /**
     * port
     */
    private static final int PORT = 6667;

    public GroupChatServer() {
        try {
            // get selector
            selector = Selector.open();
            // listenChannel
            listenChannel = ServerSocketChannel.open();
            // bind port
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // set status : non-blocking
            listenChannel.configureBlocking(false);
            // register the listenChannel to the selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            log.error("init server error", e);
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

    /**
     * listen
     */
    public void listen() {
        try {
            // get event
            while (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                // has event
                while (iterator.hasNext()) {
                    // get event
                    SelectionKey key = iterator.next();
                    // new client connect
                    if (key.isAcceptable()) {
                        SocketChannel socketChannel = listenChannel.accept();
                        // set status : non-blocking
                        socketChannel.configureBlocking(false);
                        // register the listenChannel to the selector
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        log.info("Client {} connect", socketChannel.getRemoteAddress());
                    } else if (key.isReadable()) {
                        // read event
                        readData(key);
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            log.error("Server listen error", e);
        }
    }

    /**
     * read data from client
     *
     * @param key key
     */
    private void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            // get channel
            channel = (SocketChannel) key.channel();
            // create buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            if (channel.read(buffer) != -1) {
                String msg = new String(buffer.array()).trim();
                log.info("message: {}", msg);
                // forward messages to other clients(excluding yourself)
                sendInfoOtherClients(msg, channel);
            }
        } catch (IOException e) {
            try {
                log.info("{} offline", channel.getRemoteAddress());
                System.out.println(channel.getRemoteAddress() + " 下线了!");
                // close channel
                key.cancel();
                channel.close();
            } catch (IOException ex) {
                log.error("Server readData error", e);
            }
        }
    }

    /**
     * forward messages to other clients(excluding yourself)
     *
     * @param msg  message
     * @param self channel
     */
    private void sendInfoOtherClients(String msg, SocketChannel self) throws IOException {
        // Server forwarding message
        log.info("Server forwarding message");
        // iterate over the collection
        for (SelectionKey key : selector.keys()) {
            // get channel by key
            Channel targetChannel = key.channel();
            // excluding itself
            if (targetChannel instanceof SocketChannel dest
                    && targetChannel != self) {
                // write the data in the buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                // write the data in the buffer to the channel
                dest.write(buffer);
            }
        }
    }

}
