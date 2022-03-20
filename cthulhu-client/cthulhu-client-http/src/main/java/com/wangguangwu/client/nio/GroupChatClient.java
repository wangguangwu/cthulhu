package com.wangguangwu.client.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author wangguangwu
 */
@Slf4j
public class GroupChatClient {

    /**
     * host
     */
    private static final String HOST = "127.0.0.1";

    /**
     * port
     */
    private static final int PORT = 6667;

    /**
     * selector
     */
    private Selector selector;

    /**
     * socketChannel
     */
    private SocketChannel socketChannel;

    /**
     * userName
     */
    private String userName;

    public GroupChatClient() {
        try {
            selector = Selector.open();
            // connect server
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            // set status: non-blocking
            socketChannel.configureBlocking(false);
            // regist
            socketChannel.register(selector, SelectionKey.OP_READ);
            userName = socketChannel.getLocalAddress().toString().substring(1);
            log.info("client {} is ready", userName);
        } catch (IOException e) {
            log.error("client error", e);
        }
    }

    /**
     * send data to server.
     *
     * @param info info
     */
    public void sendInfo(String info) {
        info = userName + " say: " + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            log.error("sendInfo error", e);
        }
    }

    /**
     * read info from server
     */
    public void readInfo() {
        try {
            if (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        // get channel
                        SocketChannel sc = (SocketChannel) key.channel();
                        // get buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        // read data
                        sc.read(buffer);
                        // print
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            log.error("read info from server error", e);
        }
    }

    public static void main(String[] args) {
        // start a client
        GroupChatClient chatClient = new GroupChatClient();
        // TODO：创建一个线程池
        // read data from server
        new Thread(() -> {
            while (true) {
                chatClient.readInfo();
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // send data to server
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            chatClient.sendInfo(scanner.nextLine());
        }
    }

}
