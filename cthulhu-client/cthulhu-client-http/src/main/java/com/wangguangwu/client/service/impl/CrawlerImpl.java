package com.wangguangwu.client.service.impl;

import com.wangguangwu.client.http.Response;
import com.wangguangwu.client.service.Crawler;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.util.Map;

import static com.wangguangwu.client.utils.StringUtil.parseHostAndUrl;

/**
 * visit the website corresponding to the url
 * parse response from the website.
 *
 * @author wangguangwu
 * @date 2022/2/12
 */
@Slf4j
public class CrawlerImpl implements Crawler {

    @Override
    public void crawler(String url, int port) {
        // parse host and url
        Map<String, String> hostAndUrl = parseHostAndUrl(url);
        String host = hostAndUrl.get("host");
        url = hostAndUrl.get("url");
        try (
                // Https request need to use SSLSocketFactory to create socket
                // Http request can directly create socket
                Socket socket = SSLSocketFactory.getDefault().createSocket(host, port);
                // create bufferedWriter to write request to socket
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                // socketInputStream
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
            // flush socketOutputStream
            writer.flush();

            long startTime = System.currentTimeMillis();

            // parse response
            Response response = new Response(in);
            response.setFileName(host);
            response.parse();

            long endTime = System.currentTimeMillis();
            log.info("start time: {}, end time: {}, cost time: {}", startTime, endTime, endTime - startTime);

        } catch (IOException e) {
            log.error("CrawlerImpl Crawler error: \r\n", e);
        }
    }
}
