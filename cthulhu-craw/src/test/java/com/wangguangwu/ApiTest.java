package com.wangguangwu;

import com.wangguangwu.client.startup.Bootstrap;
import com.wangguangwu.utils.BossUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * @author wangguangwu
 */
public class ApiTest {

    @Test
    void test() {
        Bootstrap bootstrap = new Bootstrap();
        BossUtil bossUtil = new BossUtil();
        String url = "https://www.zhipin.com/c101210100/?query=java&page=2&ka=page-1";
        String responseBody = bootstrap
                .url(url)
                .cookies(bossUtil.getZpsToken(url))
                .start();
        assertNotNull(responseBody);
        System.out.println(responseBody);
    }


    @Test
    void test_bio() throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        for (int i = 0; i < 10; i++) {
            System.out.println("等待连接中...");
            Socket socket = serverSocket.accept();
            System.out.println("有客户端连接：" + socket.getRemoteSocketAddress());
            executorService.execute(() -> bioHandler(socket));
        }
    }

    public static void bioHandler(Socket socket) {
        byte[] bytes = new byte[1024];
        try {
            InputStream inputStream = socket.getInputStream();
            while (inputStream.read(bytes) != -1) {
                System.out.println(Thread.currentThread().getName() + "：发送信息为：" + new String(bytes, 0, bytes.length));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
