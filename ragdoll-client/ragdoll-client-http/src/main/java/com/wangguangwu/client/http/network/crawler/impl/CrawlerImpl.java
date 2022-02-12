package com.wangguangwu.client.http.network.crawler.impl;

import com.wangguangwu.client.http.network.crawler.Crawler;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

/**
 * @author wangguangwu
 * @date 2022/2/12 11:51 PM
 * @description 爬虫程序
 */
@Slf4j
public class CrawlerImpl implements Crawler {

    @Override
    public void crawler(String url, int port) {

        try (
                // 创建一个客户端 socket
                Socket socket = new Socket(url, port);
                // 创建字符缓冲区（提高效率）
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            if (socket.isConnected()) {
                log.info("connection established, remote address:{}", socket.getRemoteSocketAddress());
            }
            // 写入请求
            writer.write("GET / HTTP/1.1\r\n");
            writer.write("HOST:" + url + "\r\n");
            writer.write("Accept: */*\r\n");
            writer.write("Connection: Keep-Alive\r\n");
            writer.write("\r\n");
            // 必须要调用 flush 方法进行刷新
            writer.flush();

            // 读取响应
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
