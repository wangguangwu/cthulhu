package com.wangguangwu.client.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wangguangwu.client.entity.Http;
import com.wangguangwu.client.entity.SalaryData;
import com.wangguangwu.client.http.Response;
import com.wangguangwu.client.service.Work;
import com.wangguangwu.client.utils.HtmlParse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.internal.StringUtil;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

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
        Map<String, String> headerMap = response.getHeaderMap();
        String cookie = headerMap.getOrDefault("Set-Cookie", "");
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
//            bufferedWriter.write("Accept-Encoding: gzip, deflate, br\r\n");
            bufferedWriter.write("Connection: Keep-Alive\r\n");
//            bufferedWriter.write("Cookie: __zp_stoken__=6ee4dKXp4OW1%2BexcNdx08WR5RZTpEYjc4O3N4U2EKa25bYyBzPCJiIzQqWC4nFQ0%2FJl1SOlY4eyYpPntkdUc8eQIyAGBRDxYyQTYoK08EfTVUOl1rTQwjU0pTdmUOc0E%2FZGRgTiRDUAVgZQc%3D; lastCity=100010000; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1646645163,1646816565; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1646816565; acw_tc=0bd17c1616468165643256786e01994d903f2342edbb9b4f0c0cefaf3cc974; __c=1646645163; __g=-; __l=l=%2Fwww.zhipin.com%2F&s=3&friend_source=0&r=https%3A%2F%2Fwww.google.com%2F&g=&s=3&friend_source=0; __a=14581530.1646645163..1646645163.14.1.14.14\r\n");
//            String cookie = "acw_tc=0bd17c1616468160681157486e019f1621345058b6f42caa5e08d945c77650";
//            bufferedWriter.write("Cookie: " + cookie + "\r\n");
            bufferedWriter.write("Cookie: acw_tc=0b6e703a16468172786205051e01a8b4fd7f12c11e1df31ea5a1216882f457; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1646817279; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1646817279; __zp_stoken__=6ee4dKXp4OW1%2BOAoYL3Y8WR5RZTpELDgmOjR4U2EKay9cUSxMYyJiIzQqFyxgdXNxJl1SOlY4e2dQPgZkfxx5BnBHYBJncyAKKiEtK08EfTVUOhMVLUshHEpTdmUOLEE%2FZGRgTiRDUAVgZQc%3D\r\n");
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
