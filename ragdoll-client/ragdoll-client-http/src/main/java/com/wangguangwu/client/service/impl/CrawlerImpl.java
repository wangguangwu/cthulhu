package com.wangguangwu.client.service.impl;

import com.wangguangwu.client.http.Response1;
import com.wangguangwu.client.http.Response2;
import com.wangguangwu.client.http.Response3;
import com.wangguangwu.client.service.Crawler;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.util.Map;

import static com.wangguangwu.client.utils.StringUtil.parseHostAndUrl;

/**
 * @author wangguangwu
 * @date 2022/2/12 11:51 PM
 * @description 爬虫程序
 */
@Slf4j
public class CrawlerImpl implements Crawler {

    @Override
    public void crawler(String url, int port) {
        // 解析 host 和 url
        Map<String, String> hostAndUrl = parseHostAndUrl(url);
        String host = hostAndUrl.get("host");
        url = hostAndUrl.get("url");
        try (
                // Https 请求使用 SSLSocketFactory，Http 请求直接创建 Socket
                Socket socket = SSLSocketFactory.getDefault().createSocket(host, port);
                // 创建字符缓冲区（提高效率）
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                InputStream in = socket.getInputStream()
        ) {
            if (socket.isConnected()) {
                log.info("connection established, local address: {}, remote address: {}", socket.getLocalSocketAddress(), socket.getRemoteSocketAddress());
                // set timeout
                socket.setSoTimeout(100000);
            }

            // write request to socket
            writer.write("GET " + url + " HTTP/1.1\r\n");
            writer.write("HOST:" + host + "\r\n");
            writer.write("Accept: */*\r\n");
            writer.write("Connection: Keep-Alive\r\n");
            writer.write("\r\n");
            // flush stream
            writer.flush();

            long startTime = System.currentTimeMillis();

//            Response1 response = new Response1(in);

//            Response2 response = new Response2(in);

            Response3 response = new Response3(in);
            response.parse();

            long endTime = System.currentTimeMillis();
            log.info("start time: {}, end time: {}, cost time: {}", startTime, endTime, endTime - startTime);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
