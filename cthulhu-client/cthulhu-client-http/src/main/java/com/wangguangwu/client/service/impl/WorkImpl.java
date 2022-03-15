package com.wangguangwu.client.service.impl;

import com.wangguangwu.client.builder.Builder;
import com.wangguangwu.client.builder.RequestBuilder;
import com.wangguangwu.client.entity.DataParse;
import com.wangguangwu.client.entity.Http;
import com.wangguangwu.client.entity.Symbol;
import com.wangguangwu.client.entity.ZhipinData;
import com.wangguangwu.client.http.Request;
import com.wangguangwu.client.http.Response;
import com.wangguangwu.client.service.Work;
import com.wangguangwu.client.utils.BossUtil;
import com.wangguangwu.client.utils.HtmlParse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.wangguangwu.client.entity.Symbol.*;
import static com.wangguangwu.client.utils.StringUtil.findFirstIndexNumberOfStr;


/**
 * @author wangguangwu
 */
@Slf4j
@Data
public class WorkImpl implements Work {

    private Request request;

    /**
     * parse url.
     *
     * @param url such as https://www.baidu.com
     */
    public WorkImpl(String url) {
        Builder builder = new RequestBuilder();
        BossUtil bossUtil = new BossUtil();
        request = builder.url(url).cookies(bossUtil.getZpsToken(url))
                .parse();
    }

    @Override
    public List<ZhipinData> work() {
        // send http request to host and get response
        InputStream in = sendRequest(request);
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
        response.setFileName(request.getHost() + request.getUri());
        Response result = response.parseResponse();
        // 302
        if (Http.MOVED_RESPONSE.contains(result.getCode())) {
            String location = result.getHeaderMap().get(Http.LOCATION);
            // access redirect website
            request.setUrl(location);
            return parseResponse(sendRequest(request));
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
        return analysisData(responseBody);
    }


    /**
     * send request to website and get response.
     *
     * @param request request
     * @return socketInputStream
     */
    private InputStream sendRequest(Request request) {
        String host = request.getHost();
        String url = request.getUrl();
        int port = request.getPort();
        String cookies = request.getCookies();
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
            bufferedWriter.write("Connection: Keep-Alive\r\n");
            System.out.println("wang:" + cookies);
            bufferedWriter.write("Cookie: " + cookies + "\r\n");
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

    /**
     * parse zhipin data
     *
     * @param html data from zhipin
     * @return zhipinData
     */
    private List<ZhipinData> analysisData(String html) {
        html = HtmlParse.formatHtml(html);
        Document doc = Jsoup.parse(html);

        Elements rows = new Elements();

        if (doc.select(DataParse.JOB_LIST).size() > 0) {
            rows = doc.select("div[class=job-list]").get(0).select("ul");
        }

        List<ZhipinData> list = new ArrayList<>();

        if (rows.size() == 0) {
            log.info("no results");
        } else {
            for (int i = 0; i < rows.get(0).select(DataParse.LI).size(); i++) {
                ZhipinData data = new ZhipinData();

                Elements jobPrimary = rows.get(0).select("li").get(i).select("[class=job-primary]");
                // basic part
                Elements infoPrimary = jobPrimary.select("[class=info-primary]");
                Elements infoAppend = jobPrimary.select("[class=info-append clearfix]");

                // primary-box
                Elements primaryBox = infoPrimary.select("[class=primary-wrapper]")
                        .select("[class=primary-box]");
                // company-text
                Elements companyText = infoPrimary.select("[class=info-company]")
                        .select("[class=company-text]");

                data.setCompanyName(companyText.select("h3").text());

                final Elements p = companyText.select("p");
                final String companyInfo = p.text();
                // companyIndustry
                final String companyIndustry = p.select("a").text();

                data.setCompanyIndustry(companyIndustry);
                String companyStatus = companyInfo.substring(companyIndustry.length());

                int statusIndex = findFirstIndexNumberOfStr(companyStatus);

                if (statusIndex > 0) {
                    data.setCompanySize(companyStatus.substring(statusIndex));
                    companyStatus = companyStatus.substring(0, statusIndex);
                }
                data.setCompanyStatus(companyStatus);

                // jobTitle
                String jobTitle = primaryBox.select("[class=job-title]").text();
                int index = jobTitle.lastIndexOf(Symbol.SPACE);
                if (index != -1) {
                    data.setJobName(jobTitle.substring(0, index));
                    data.setCompanyAddress(jobTitle.substring(index + 1));
                }

                // job-limit clearfix
                String jobLimitClearfix = primaryBox.select("[class=job-limit clearfix]").text();
                String[] strings = jobLimitClearfix.split(Symbol.SPACE);
                data.setJobSalary(strings[0]);
                data.setJobEducationRequire(strings[1]);

                // tags
                data.setJobSkillRequire(infoAppend.select("[class=tags]").text());
                // infoDesc
                data.setJobInfoDesc(infoAppend.select("[class=info-desc]").text());

                list.add(data);
            }
        }
        return list;
    }

}
