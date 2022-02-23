package com.wangguangwu.client.service.impl;

import com.wangguangwu.client.entity.Robot;
import com.wangguangwu.client.http.Response;
import com.wangguangwu.client.service.Crawler;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.util.*;

import static com.wangguangwu.client.entity.Commons.*;
import static com.wangguangwu.client.entity.Symbol.SEMICOLON;
import static com.wangguangwu.client.utils.StringUtil.parseHostAndUrl;

/**
 * visit the website corresponding to the url
 * parse response from the website.
 *
 * @author wangguangwu
 */
@Slf4j
public class CrawlerImpl implements Crawler {

    @Override
    public Map<String, String> crawler(String url, int port) {
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
            writer.write("HOST: " + host + "\r\n");
            writer.write("Accept: */*\r\n");
            writer.write("Connection: Keep-Alive\r\n");
            writer.write("\r\n");
            // flush socketOutputStream
            writer.flush();

            long startTime = System.currentTimeMillis();

            // parse response
            Response response = new Response(in);
            response.setFileName(host);
            int bufferSize = 1024;
            if (host.endsWith(COM) && ROBOTS.equals(url)) {
                bufferSize = 512;
            }
            response.setBufferSize(bufferSize);
            Map<String, String> responseMap = response.parse();

            long endTime = System.currentTimeMillis();
            log.info("start time: {}, end time: {}, cost time: {}", startTime, endTime, endTime - startTime);

            return responseMap;
        } catch (IOException e) {
            log.error("CrawlerImpl Crawler error: ", e);
        }
        return null;
    }

    @Override
    public void parseRobotsProtocol(String host) {

        Map<String, String> responseMap = crawler(host + "/robots.txt", 443);
        // ex: User-agent: baidu\r\n Disallow: /\r\n\r\n
        List<String> list = Arrays.asList(responseMap.get("responseBody").split("\r\n\r\n"));

        List<Robot> robotList = new ArrayList<>();

        list.forEach(data -> {
            Robot robot = new Robot();
            List<String> strings = Arrays.asList(data.split("\r\n"));
            // ex: Disallow: *?from=*
            List<String> disAllows = new ArrayList<>();
            // ex: Allow: *?from=*
            List<String> allows = new ArrayList<>();

            strings.forEach(
                    str -> {
                        if (str.contains(SEMICOLON)) {
                            int index = str.indexOf(SEMICOLON);
                            String value = str.substring(index + 2);
                            if (str.startsWith(USER_AGENT)) {
                                robot.setUserAgentName(value);
                            }
                            if (str.startsWith(DIS_ALLOW)) {
                                disAllows.add(value);
                            }
                            if (str.startsWith(ALLOW)) {
                                allows.add(value);
                            }
                        }
                    }
            );
            robot.setAllows(allows);
            robot.setDisAllows(disAllows);
            robotList.add(robot);
        });

        System.out.println(robotList.size());
    }
}
