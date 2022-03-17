package com.wangguangwu.client.service.impl;

import com.wangguangwu.client.builder.Builder;
import com.wangguangwu.client.builder.RequestBuilder;
import com.wangguangwu.client.entity.Http;
import com.wangguangwu.client.http.Request;
import com.wangguangwu.client.http.Response;
import com.wangguangwu.client.service.Work;
import com.wangguangwu.client.utils.HtmlParse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static com.wangguangwu.client.entity.Symbol.*;


/**
 * @author wangguangwu
 */
@Slf4j
@Data
public class WorkImpl implements Work {

    /**
     * request.
     */
    private Request request;

    /**
     * response.
     */
    private Response response;

    /**
     * build request.
     *
     * @param url     url
     * @param cookies cookies
     */
    public WorkImpl(String url, String cookies) {
        Builder builder = new RequestBuilder();
        request = builder.url(url).cookies(cookies)
                .parse();
    }

    @Override
    public String work() {
        // send http request to host and get response
        InputStream in = sendRequest(request);
        // parse response
        return parseResponse(in);
    }

    /**
     * parse socketInputStream
     *
     * @param in socketInputStream
     * @return responseBody
     */
    private String parseResponse(InputStream in) {
        // parse response
        response = new Response(in);
        Response result = response.parseResponse();
        // 302
        if (Http.MOVED_RESPONSE.contains(result.getCode())) {
            String location = result.getHeaderMap().get(Http.LOCATION);
            // access redirect website
            request.setUrl(location);
            return parseResponse(sendRequest(request));
        }
        return HtmlParse.formatHtml(result.getResponseBody());
    }

    /**
     * send request to website and get socketInputStream.
     *
     * @param request request
     * @return socketInputStream
     */
    private InputStream sendRequest(Request request) {
        String host = request.getHost();
        String uri = request.getUri();
        int port = request.getPort();
        String cookies = request.getCookies();
        String protocol = request.getProtocol();
        try {
            InetAddress inetAddress = InetAddress.getByName(host);
            // create socket to send request
            Socket socket = port == 80
                    ? new Socket(inetAddress.getHostAddress(), port) : SSLSocketFactory.getDefault().createSocket(inetAddress.getHostAddress(), port);

            if (socket.isConnected()) {
                log.info("connection established, remote address: {}", socket.getRemoteSocketAddress());
            }

            // write http request into socket
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            bufferedWriter.write("GET " + uri + SPACE + protocol + "\r\n");
            bufferedWriter.write("Cookie: " + cookies + "\r\n");
            bufferedWriter.write("HOST: " + host + "\r\n");
            bufferedWriter.write(Http.REQUEST_TEMPLATE);
            // flush
            bufferedWriter.flush();

            return socket.getInputStream();
        } catch (IOException e) {
            log.error("Cthulhu client send http request error", e);
        }
        return null;
    }

}
