package com.wangguangwu.client.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wangguangwu.client.entity.Http;
import com.wangguangwu.client.entity.Robot;
import com.wangguangwu.client.http.Response;
import com.wangguangwu.client.service.Work;
import com.wangguangwu.client.utils.HtmlParse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static com.wangguangwu.client.entity.Symbol.*;


/**
 * @author wangguangwu
 */
@Slf4j
@Data
public class WorkImpl implements Work {

    /**
     * protocol, such as HTTP/1.1
     */
    private String protocol;

    /**
     * host, such as www.wangguangwu.com
     */
    private String host;

    /**
     * uri, such as "/"
     */
    private String uri;

    /**
     * port, such as 8080
     */
    private int port;

    @Override
    public void work(String url) {
        parseHostAndUrl(url);
        // access the website
        accessWebsite(host, uri, port);
    }

    private void accessWebsite(String host, String url, int port) {
        try {
            InputStream in = sendRequest(host, url, port);

            Response response = new Response(in);
            response.setFileName(host + url);
            Response result = response.parseResponse();
            // 302
            if (Http.MOVED_RESPONSE.contains(result.getCode())) {
                String location = result.getHeaderMap().get("location");
                accessWebsite(host, location, port);
            }
            String responseBody = result.getResponseBody();

//            HtmlParse.parseHtml(responseBody);

        } catch (IOException e) {
            log.error("Cthulhu client access website error", e);
        }
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

        // create socket to send request
        Socket socket = port == 80
                ? new Socket(host, port) : SSLSocketFactory.getDefault().createSocket(host, port);

        // write http request into socket
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        bufferedWriter.write("GET " + url + SPACE + protocol + "\r\n");
        bufferedWriter.write("HOST: " + host + "\r\n");
        bufferedWriter.write("Accept: */*\r\n");
        bufferedWriter.write("Connection: Keep-Alive\r\n");
        bufferedWriter.write("\r\n");
        bufferedWriter.flush();

        return socket.getInputStream();
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
                    ? urlObject.getPath() : SLASH + urlObject.getPath();
            uri = StrUtil.isEmpty(urlObject.getQuery())
                    ? uri : uri + "?" + urlObject.getQuery();
        } catch (MalformedURLException e) {
            log.error("Cthulhu client parseHostAndUrl error: ", e);
        }
    }

}
