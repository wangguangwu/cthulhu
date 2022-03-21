package com.wangguangwu.client.nio;

import java.nio.ByteBuffer;

/**
 * @author wangguangwu
 */
public class BufferDemo {

    public static void main(String[] args) {
        System.out.println("=================初始化-默认写入模式下查看三个重要的指标=========================");
        // 申请一个三字节大小的堆内存缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(3);
        // 申请一个三字节大小的堆外内存缓冲区
//        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3);
        printBuffer(byteBuffer);

        // 写入模式下的 limit 大小等于 capacity 大小
        System.out.println("=================写入两个字节-查看三个重要的指标=========================");
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 2);
        printBuffer(byteBuffer);

        // 切换为读取方式
        System.out.println("=================转换为读取模式-查看三个重要的指标=========================");
        byteBuffer.flip();
        printBuffer(byteBuffer);

        // 从头往下读，调用 flip 方法转为读取模式时，position 归零，limit = position
        System.out.println("=================读取第一个值-查看三个重要的指标=========================");
        System.out.println("读取到第一个值:" + byteBuffer.get());
        printBuffer(byteBuffer);

        System.out.println("=================读取第二个值-查看三个重要的指标=========================");
        System.out.println("读取到第二个值:" + byteBuffer.get());
        printBuffer(byteBuffer);

        // 此时在读取模式下，如果想要再次写入数据，需要调用 clear 或者 compact 方法
        // clear 方法清除整个缓冲区
        // compact 方法仅清除已阅读的数据，转为写入模式
        System.out.println("=================compact 仅清除已阅读的数据转换为写入模式-查看三个重要的指标=========================");
        byteBuffer.compact();
        printBuffer(byteBuffer);

    }

    private static void printBuffer(ByteBuffer byteBuffer) {
        System.out.println("capacity 容量：" + byteBuffer.capacity());
        System.out.println("position 位置：" + byteBuffer.position());
        System.out.println("limit 限制：" + byteBuffer.limit());
    }

}
