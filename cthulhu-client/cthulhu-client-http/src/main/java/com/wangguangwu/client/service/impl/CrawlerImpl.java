package com.wangguangwu.client.service.impl;

import com.wangguangwu.client.entity.Robot;
import com.wangguangwu.client.http.Response;
import com.wangguangwu.client.service.Crawler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wangguangwu.client.entity.Commons.*;
import static com.wangguangwu.client.entity.Http.*;
import static com.wangguangwu.client.entity.Symbol.*;

/**
 * visit the website corresponding to the url
 * parse response from the website.
 *
 * @author wangguangwu
 */
@Slf4j
@Getter
@Setter
public class CrawlerImpl implements Crawler {

    /**
     * url waiting to be crawled.
     */
    private static List<String> allWaitUrl = new ArrayList<>();

    /**
     * crawled url.
     */
    private static List<String> allOverUrl = new ArrayList<>();

    /**
     * Record the depth of all urls for crawling judgment.
     */
    private static Map<String, Integer> allUrlDepth = new HashMap<>();

    /**
     * Crawl depth。
     */
    private static int maxDepth = 2;


    //===================================

    private static String host;

    private static String url;

    private static int port;

    private static String protocol;

    List<Robot> robotList;

    /**
     * 正则表达式
     */
    Pattern p = Pattern.compile("<a .*href=.+</a>");

    @Override
    public void work(String visitUrl) {
        // parse host、url、http port
        parseHostAndUrl(visitUrl);
        // access robots.txt under the website
        parseRobotsProtocol();
        //

    }

    @Override
    public Map<String, String> crawler(String visitUrl) {
        try (
                // Https request need to use SSLSocketFactory to create socket
                // Http request can directly create socket
                Socket socket = port == 443
                        ? SSLSocketFactory.getDefault().createSocket(host, port) : new Socket(host, port);
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
            writer.write("GET " + visitUrl + " HTTP/1.1\r\n");
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


    /**
     * /**
     * get the robot's protocol of the specified website.
     */
    private void parseRobotsProtocol() {

        Map<String, String> responseMap = crawler("/robots.txt");
        // ex: User-agent: baidu\r\n Disallow: /\r\n\r\n
        List<String> list = Arrays.asList(responseMap.get("responseBody").split("\r\n\r\n"));

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
    }

    /**
     * parse host、url、protocol and port.
     *
     * @param str host + url
     */
    public static void parseHostAndUrl(String str) {

        if (str.startsWith(HTTPS_PROTOCOL_START)) {
            protocol = HTTPS_PROTOCOL;
            port = 443;
        } else {
            protocol = HTTP_PROTOCOL;
            port = 80;
        }

        if (str.contains(DOUBLE_SLASH)) {
            str = str.substring(str.indexOf(DOUBLE_SLASH) + 2);
        }

        host = str.contains(COM) ? str.substring(0, str.indexOf(COM) + 4) : str;
        url = str.contains(COM) ? str.substring(str.indexOf(COM) + 4) : "";

        url = url.startsWith(SLASH) ? url : SLASH + url;
    }

}
