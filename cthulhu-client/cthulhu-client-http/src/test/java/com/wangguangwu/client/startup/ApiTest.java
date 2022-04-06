package com.wangguangwu.client.startup;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * @author wangguangwu
 */
public class ApiTest {

    @Test
    public void test_nio() {
        // 创建一个 buffer
        IntBuffer buffer = IntBuffer.allocate(5);
        // 存放数据
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(i);
        }
        // 读写切换
        // 切换成读写模式
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }

    @Test
    public void test_fileChannel_write() throws IOException {
        String content = "Hello World";
        // 创建一个输出流
        FileOutputStream outputStream = new FileOutputStream("/Users/wangguangwu/Desktop/hello.txt");
        // 获取通道
        FileChannel channel = outputStream.getChannel();
        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        // 写入 byteBuffer
        byteBuffer.put(content.getBytes());
        // 切换模式
        byteBuffer.flip();
        // 写入通道
        channel.write(byteBuffer);
        // 关闭
        channel.close();
        outputStream.close();
    }

    @Test
    public void test_fileChannel_read() throws IOException {
        // 获取输入流
        FileInputStream inputStream = new FileInputStream("/Users/wangguangwu/Desktop/hello.txt");
        // 获取通道
        FileChannel channel = inputStream.getChannel();
        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        // 读取数据
        channel.read(byteBuffer);
        // 打印
        System.out.println(new String(byteBuffer.array(), 0, byteBuffer.position()));
        // 关闭
        channel.close();
        inputStream.close();
    }

    @Test
    public void test_fileChanel_copy() {

        try (
                // 创建输入流和输出流
                FileInputStream inputStream = new FileInputStream("/Users/wangguangwu/Desktop/hello.txt");
                FileOutputStream outputStream = new FileOutputStream("/Users/wangguangwu/Desktop/world.txt");
                // 创建管道
                FileChannel inChannel = inputStream.getChannel();
                FileChannel outChannel = outputStream.getChannel();) {
            // 创建缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1);
            while (inChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                outChannel.write(byteBuffer);
                // 清空重置
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_transferFrom() {
        try (
                // 创建输入流和输出流
                FileInputStream inputStream = new FileInputStream("/Users/wangguangwu/Desktop/hello.txt");
                FileOutputStream outputStream = new FileOutputStream("/Users/wangguangwu/Desktop/world.txt");
                // 创建管道
                FileChannel inChannel = inputStream.getChannel();
                FileChannel outChannel = outputStream.getChannel()) {
            // 是用 transferForm 方法拷贝数据
            outChannel.transferFrom(inChannel, 0, inChannel.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
