package com.wangguangwu.client.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wangguangwu.client.entity.Http;
import com.wangguangwu.client.entity.ZhipinData;
import com.wangguangwu.client.http.Response;
import com.wangguangwu.client.service.Work;
import com.wangguangwu.client.utils.HtmlParse;
import com.wangguangwu.client.utils.TokenUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.wangguangwu.client.entity.Symbol.*;


/**
 * @author wangguangwu
 */
@Slf4j
@Data
public class WorkImpl implements Work {

    /**
     * url, such as https://www.baidu.com
     */
    public String url;

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

    /**
     * parse url.
     *
     * @param url such as https://www.baidu.com
     */
    public WorkImpl(String url) {
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
            log.error("Cthulhu client parseUrl error", e);
        }
    }

    @Override
    public List<ZhipinData> work() {
        // send http request to host and get response
        InputStream in = sendRequest(host, uri, port, "__zp_stoken__=8c31dKUgZRUI%2FLWwUVXklWzNtVTA1DUAdcQMgDSRTLkstNF0tbERLRH8jJ18ufCRfHnZfOl9DXUJEJjhnMEIieWpAbk5Gdmg1IXEFKAEfSkQzEUIKLTRRKAJZFU9ZKglNXE9tTi08UD94PDk%3D;");
        // access the website
        String responseBody = parseResponse(in);
        // parse responseBody
        return parseResponseBody(responseBody);
    }

    /**
     * parse the response.
     *
     * @param in socketInputStream
     * @return responseBody
     */
    private String parseResponse(InputStream in) {
        // parse response
        Response response = new Response(in);
        response.setFileName(host + uri);
        Response result = response.parseResponse();
        // 302
        if (Http.MOVED_RESPONSE.contains(result.getCode())) {
            String location = result.getHeaderMap().get(Http.LOCATION);
            // access redirect website
            String cookie = TokenUtil.getZpToken(location);
            return parseResponse(sendRequest(host, location, port, cookie));
        }
        return result.getResponseBody();
    }

    /**
     * parse responseBody and get java salary data.
     *
     * @param responseBody responseBody
     * @return list of salaryData
     */
    private List<ZhipinData> parseResponseBody(String responseBody) {
        return HtmlParse.analysisData(responseBody);
    }


    /**
     * send request to website and get response.
     *
     * @param host host
     * @param url  url
     * @param port port
     * @return socketInputStream
     */
    private InputStream sendRequest(String host, String url, int port, String cookie) {
        try {
            InetAddress inetAddress = InetAddress.getByName(host);
            String protocol = port == 80 ? "HTTP/1.0" : "HTTP/1.1";
            // create socket to send request
            Socket socket = port == 80
                    ? new Socket(inetAddress.getHostAddress(), port) : SSLSocketFactory.getDefault().createSocket(inetAddress.getHostAddress(), port);

            if (socket.isConnected()) {
                log.info("connection established, remote address: {}", socket.getRemoteSocketAddress());
            }

            // write http request into socket
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            bufferedWriter.write("GET " + url + SPACE + protocol + "\r\n");
            bufferedWriter.write("HOST: " + host + "\r\n");
            bufferedWriter.write("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:97.0) Gecko/20100101 Firefox/97.0\r\n");
            bufferedWriter.write("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\r\n");
            bufferedWriter.write("Accept-Language: en-US,en;q=0.5\r\n");
            // TODO：gzip decrypt
            bufferedWriter.write("Connection: Keep-Alive\r\n");
            if (!StrUtil.isEmpty(cookie)) {
                bufferedWriter.write("Cookie: " + cookie + "\r\n");
            }
            bufferedWriter.write("Referer: https://www.zhipin.com/web/common/security-check.html?seed=KZwFuez5EFZc0c%2F8bsInOLZkHG5%2FQWED4X%2FxzsvB8AY%3D&name=e5e75d46&ts=1646817278645&callbackUrl=%2Fjob_detail%2F%3Fquery%3Djava%26city%3D101210100%26industry%3D%26position%3D&srcReferer=\r\n");
            bufferedWriter.write("Upgrade-Insecure-Requests: 1\r\n");
            bufferedWriter.write("Sec-Fetch-Dest: document\r\n");
            bufferedWriter.write("Sec-Fetch-Mode: navigate\r\n");
            bufferedWriter.write("Sec-Fetch-Site: none\r\n");
            bufferedWriter.write("Sec-Fetch-User: ?1\r\n");
            bufferedWriter.write("\r\n");
            // flush
            bufferedWriter.flush();

            return socket.getInputStream();
        } catch (IOException e) {
            log.error("Cthulhu client send http request error", e);
        }
        return null;
    }

}
