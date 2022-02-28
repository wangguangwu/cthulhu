package com.wangguangwu.client.service.impl;

import com.wangguangwu.client.entity.Robot;
import com.wangguangwu.client.exception.BadResponseException;
import com.wangguangwu.client.http.Response;
import com.wangguangwu.client.service.Work;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.*;

import static com.wangguangwu.client.entity.Http.*;
import static com.wangguangwu.client.entity.Symbol.*;


/**
 * @author wangguangwu
 */
@Slf4j
@Data
public class WorkImpl implements Work {

    private String protocol;

    private String host;

    private String uri;

    private int port;

    private static List<String> badResponse = List.of("401", "403", "404");

    private static List<String> movedResponse = List.of("301", "302");

    private static Map<String, String> websiteCharset = new HashMap<>();

    private static String defaultCharset = "utf-8";

    private List<Robot> robotList = new ArrayList<>();

    @Override
    public void work(String url) {

        parseHostAndUrl(url);

        // access the website
        accessWebsite(host, uri, port);
        // parse
        parseRobotsProtocol(host, port);

        System.out.println();
    }

    private void accessWebsite(String host, String url, int port) {
        // pre visit the website
        int responseLength = preVisit(host, url, port);

        // visit again
        try {
            InputStream in = sendRequest(host, url, port);

            Response response = new Response(in);
            response.setBufferSize(responseLength);
            response.setFileName(host + url);
            response.parse();

        } catch (IOException e) {
            log.error("WorkImpl visit error: ", e);
        }
    }

    /**
     * pre visit the website to get the encoding format.
     */
    private int preVisit(String host, String url, int port) {
        String charset = defaultCharset;
        if (websiteCharset.containsKey(host)) {
            charset = websiteCharset.get(host);
        }
        try {
            InputStream in = sendRequest(host, url, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));

            Map<String, String> responseHeader = new HashMap<>();

            String line;
            String key;
            String value;
            int index;
            boolean parseLine = true;
            String responseCode = "";
            StringBuilder responseBesidesBody = new StringBuilder();

            while (true) {
                line = reader.readLine();
                responseBesidesBody.append(line);
                if (parseLine) {
                    responseCode = line.split(SPACE)[2];
                    if (badResponse.contains(responseCode)) {
                        log.error("fail to access website: {}", host);
                        throw new BadResponseException("bad response code=========>" + responseCode);
                    }
                    parseLine = false;
                    continue;
                }
                if (BLANK.equals(line)) {
                    break;
                }
                if (line.contains(SEMICOLON)) {
                    index = line.indexOf(SEMICOLON);
                    key = line.substring(0, index);
                    value = line.substring(index + 2);
                    responseHeader.put(key, value);
                }
            }

            if (movedResponse.contains(responseCode)) {
//                // redirect the new website
//                String redirectLocation = responseHeader.get("Location");
//                Map<String, String> hostAndUrl = parseHostAndUrl(redirectLocation, port);
//                preVisit(hostAndUrl.get(("host")), hostAndUrl.get("url"), Integer.parseInt(hostAndUrl.get("port")));
            }

            if (responseHeader.containsKey(CONTENT_TYPE)
                    && responseHeader.get(CONTENT_TYPE).contains(CHARSET)) {

                String contentValue = responseHeader.get(CONTENT_TYPE);
                int charsetIndex = contentValue.indexOf(CHARSET);
                charset = contentValue.substring(charsetIndex + 8);
            }

            websiteCharset.put(host, charset);

//            // after first access
//            int responseBesideBodyLength = responseBesidesBody.toString()
//                    .getBytes(charset).length;
//            int responseBodyLength = Integer.parseInt(responseHeader.get(CONTENT_LENGTH));
//            return responseBesideBodyLength + responseBodyLength;
        } catch (IOException e) {
            log.error("WorkImpl preVisit error: ", e);
        }
        return -1;
    }


    /**
     * send request to website and get response.
     *
     * @param host host
     * @param url  url
     * @param port port
     * @return socket.getInputStream
     * @throws IOException IOException
     */
    private static InputStream sendRequest(String host, String url, int port) throws IOException {
        String protocol = port == 80 ? "HTTP/1.0" : "HTTP/1.1";

        Socket socket = port == 80
                ? new Socket(host, port) : SSLSocketFactory.getDefault().createSocket(host, port);

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.write("GET " + url + SPACE + protocol + "\r\n");
        bufferedWriter.write("HOST: " + host + "\r\n");
        bufferedWriter.write("Accept: */*\r\n");
        bufferedWriter.write("Connection: Keep-Alive\r\n");
        bufferedWriter.write("\r\n");
        bufferedWriter.flush();

        return socket.getInputStream();
    }

    /**
     * /**
     * get the robot's protocol of the specified website.
     *
     * @param host host
     * @param port port
     */
    private void parseRobotsProtocol(String host, int port) {
        StringBuilder responseBody = new StringBuilder();
        try {
            InputStream in = sendRequest(host, "/robots.txt", port);
            String charset = websiteCharset.getOrDefault(host, "utf-8");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));

            String line;
            boolean parseLine = true;
            boolean parseHeader = true;
            String responseCode;
            while ((line = reader.readLine()) != null) {
                if (parseLine) {
                    responseCode = line.split(SPACE)[2];
                    if (badResponse.contains(responseCode)) {
                        log.error("fail to access website: {}", host);
                        throw new BadResponseException("bad response code=========>" + responseCode);
                    }
                    parseLine = false;
                    continue;
                }
                if (parseHeader) {
                    if (SPACE.equals(line)) {
                        parseHeader = false;
                    }
                    continue;
                }
                responseBody.append(line);
            }
        } catch (IOException e) {
            log.error("WorkImpl parseRobotsProtocol error: ", e);
        }
        // ex: User-agent: baidu\r\n Disallow: /\r\n\r\n
        List<String> list = Arrays.asList(responseBody.toString().split("\r\n\r\n"));

        CrawlerImpl.parse(list, robotList);
    }

    /**
     * parse host、uri、protocol and port.
     *
     * @param url uniform resource locator, such as https://www.baidu.com/index.html
     */
    public void parseHostAndUrl(String url) {
        try {
            URL urlObject = new URL(url);
            protocol = urlObject.getProtocol();
            host = urlObject.getHost();
            port = urlObject.getPort() != -1
                    ? urlObject.getPort() : urlObject.getDefaultPort();
            uri = urlObject.getPath().startsWith(SLASH)
                    ? urlObject.getPath() : urlObject.getPath() + SLASH;
        } catch (MalformedURLException e) {
            log.error("WorkImpl parseHostAndUrl error: ", e);
        }
    }

}
