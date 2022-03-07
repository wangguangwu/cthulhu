package com.wangguangwu.client.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wangguangwu.client.entity.Http;
import com.wangguangwu.client.entity.SalaryData;
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
    public List<SalaryData> work() {
        // access the website
        String responseBody = accessWebsite(host, uri, port);
        // parse responseBody
        return parseResponseBody(responseBody);
    }

    /**
     * send http request to website and parse responseBody.
     *
     * @param host host
     * @param uri  uri
     * @param port port
     */
    private String accessWebsite(String host, String uri, int port) {
        // send http request to host and get response
        InputStream in = sendRequest(host, uri, port);
        // parse response
        Response response = new Response(in);
        response.setFileName(host + uri);
        Response result = response.parseResponse();
        // 302
        if (Http.MOVED_RESPONSE.contains(result.getCode())) {
            String location = result.getHeaderMap().get(Http.LOCATION);
            // access redirect website
            return accessWebsite(host, location, port);
        }
        return result.getResponseBody();
    }

    /**
     * parse responseBody and get java salary data.
     *
     * @param responseBody responseBody
     * @return list of salaryData
     */
    private List<SalaryData> parseResponseBody(String responseBody) {
        return HtmlParse.parseHtml(responseBody);
    }


    /**
     * send request to website and get response.
     *
     * @param host host
     * @param url  url
     * @param port port
     * @return socketInputStream
     */
    private InputStream sendRequest(String host, String url, int port) {
        try {
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

//            String a = "GET /job_detail/?query=%E5%90%8C%E8%8A%B1%E9%A1%BA&city=101210100&industry=&position=100101 HTTP/1.1\r\n" +
//                    "Host: www.zhipin.com\r\n" +
//                    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:97.0) Gecko/20100101 Firefox/97.0\r\n" +
//                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\r\n" +
//                    "Accept-Language: en-US,en;q=0.5\r\n" +
//                    "Accept-Encoding: gzip, deflate, br\r\n" +
//                    "Connection: keep-alive\r\n" +
//                    "Cookie: __zp_stoken__=0177dYRkien0wJB94Nj1QZ1UjLHMpIDQ9MjtiKCV3KTFpDGt4IH5tIS8XW31fLXs8LSZHcgdSIj1kNmEfO3NKalljSGsxOCEZN1M%2BUAsVKBpwQQMXWSgGDwU6KxwOQwN3bx91BnVbXUByBR0%3D; lastCity=100010000; __c=1646645163; __g=-; __a=14581530.1646645163..1646645163.1.1.1.1; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1646645163; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1646645163\r\n" +
//                    "Upgrade-Insecure-Requests: 1\r\n" +
//                    "Sec-Fetch-Dest: document\r\n" +
//                    "Sec-Fetch-Mode: navigate\r\n" +
//                    "Sec-Fetch-Site: none\r\n" +
//                    "Sec-Fetch-User: ?1\r\n";
//            bufferedWriter.write(a);
//            bufferedWriter.write("\r\n");
//            bufferedWriter.flush();

            return socket.getInputStream();
        } catch (IOException e) {
            log.error("Cthulhu client send http request error", e);
        }
        return null;
    }

}
