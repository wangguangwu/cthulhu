package com.wangguangwu.client.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 使用NIO访问百度的数据
 */
public class BaiduClient {

    private final static String HOST_NAME = "www.baidu.com";

    private final static int PORT = 80;

    public static void main(String[] args) throws IOException, InterruptedException {

        SocketChannel socketChannel = SocketChannel.open();
        // 需要注意的是这里设置成非阻塞 connect 和 write  是有先后顺序的 如果设置成非阻塞会报 NotYetConnectedException
        // write 操作必须等connet成功
        // 设置成非阻塞
        socketChannel.configureBlocking(false);

        //连接百度服务器
        socketChannel.connect(new InetSocketAddress(HOST_NAME, PORT));
        //判断是否完成连接 如果设置成非阻塞的这步操作一定要有，否则执行write操作时会报错 ，而且一定要放到write操作前面
        //根据方法源码注释（connect 方法的注释）——》the connection operation must later be completed by
        //     * invoking the {@link #finishConnect finishConnect} method.
        //设置为非阻塞时，需要配合此方法
        while (!socketChannel.finishConnect()) {
            Thread.yield();
        }

        //创建Http 请求行 请求头 一定要符合HTTP协议
        StringBuilder requestMsg = new StringBuilder();
        requestMsg.append("GET / HTTP/1.1\r\n");
        requestMsg.append("Host:www.baidu.com\r\n");
        requestMsg.append("\r\n");

        //往服务器写入请求信息
        ByteBuffer requestBuf = ByteBuffer.wrap(requestMsg.toString().getBytes());
        Thread.sleep(5000L);
        socketChannel.write(requestBuf);

        //处理百度返回的数据
        ByteBuffer reponseBuf = ByteBuffer.allocate(1024);

        int read = socketChannel.read(reponseBuf);
        while (read != -1) {
            reponseBuf.flip();
            while (reponseBuf.hasRemaining()) {
                byte[] resContext = new byte[reponseBuf.limit()];
                reponseBuf.get(resContext);
                String resStr = new String(resContext);
                String[] split = resStr.split("\r\n");
                for (String s :
                        split) {
                    System.out.println(s);
                }
            }
            reponseBuf.clear();
            read = socketChannel.read(reponseBuf);
        }
    }

}

