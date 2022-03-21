package com.wangguangwu.client.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 多路选择器 demo。
 *
 * @author wangguangwu
 */
public class SelectorDemo {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        // 生成多路选择器
        Selector selector = Selector.open();

        // 注册感兴趣的事件
        socketChannel.register(selector, SelectionKey.OP_READ);

        // 由 accept 轮询，变成了事件通知的方式
        while (true) {
            int select = selector.select();
            if (select == 0) {
                continue;
            }

            // 判断不同的事件类型，执行对应的逻辑处理
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {

                }

                if (selectionKey.isReadable()) {

                }

                if (selectionKey.isWritable()) {

                }

                if (selectionKey.isConnectable()) {

                }

                iterator.remove();
            }
        }
    }

}
