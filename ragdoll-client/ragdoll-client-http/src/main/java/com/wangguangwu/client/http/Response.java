package com.wangguangwu.client.http;

import com.wangguangwu.client.service.Crawler;
import com.wangguangwu.client.service.impl.CrawlerImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.wangguangwu.client.entity.Symbol.*;
import static com.wangguangwu.client.utils.FileUtil.saveData2File;
import static com.wangguangwu.client.utils.HtmlParse.formatHtml;
import static com.wangguangwu.client.utils.StreamUtil.stream2ByteArray;
import static com.wangguangwu.client.utils.StringUtil.map2String;

/**
 * @author wangguangwu
 * @date 2022/2/14 6:06 AM
 * @description Response 响应
 */
@Slf4j
@Getter
@Setter
public class Response {

    /**
     * 输入流
     */
    private InputStream inputStream;

    /**
     * 访问站点
     */
    private String host;

    /**
     * 协议
     */
    private String protocol;

    /**
     * 响应码
     */
    private int code;

    /**
     * 状态描述
     */
    private String description;

    /**
     * 响应头
     */
    private final Map<String, String> headerMap = new HashMap<>();

    /**
     * 响应体
     */
    private String responseBody;

    public Response(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * 解析 response 响应
     */
    public void parse() {

 /*       BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder("");
        String readLine;

        try {
            while (((readLine = br.readLine()) != null)) {
                System.out.println(readLine);
                response.append(readLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        // 将输入流转化为 byte 数组
        byte[] content = stream2ByteArray(inputStream);

        // 解析请求行
        boolean parseResponseLine = true;

        for (int index = 0, i = 0; i < Objects.requireNonNull(content).length - 1; i++) {
            // \r\n 结尾 表示一行
            if (content[i] == RETURN && content[i + 1] == WRAP) {
                // 响应行
                if (parseResponseLine) {
                    // 一行的长度
                    int length = i - index;
                    // 创建缓冲数组
                    byte[] subBytes = new byte[length];
                    // 复制第一行数据
                    System.arraycopy(content, index, subBytes, 0, length);
                    String[] responseLine = new String(subBytes, StandardCharsets.UTF_8).split(SPACE);

                    protocol = responseLine[0];
                    code = Integer.parseInt(responseLine[1]);
                    description = responseLine[2];

                    log.info("protocol:{}, code:{}, description:{}", protocol, code, description);

                    parseResponseLine = false;
                    index = i + 2;
                    continue;
                }
                // 响应头
                // 一行内容长度
                int length = i - index;
                // 创建对应的缓冲数组
                byte[] subBytes = new byte[length];

                // 复制数据
                System.arraycopy(content, index, subBytes, 0, length);

                // 到下一行
                index = i + 2;

                // 读到响应空行
                if (subBytes.length == 0) {
                    log.info("responseHeader:\r\n{}", map2String(headerMap));
                    // 响应体
                    int responseBodyLength = 0;
                    // 响应体字节长度
                    if (headerMap.containsKey("Content-Length")) {
                        responseBodyLength = Integer.parseInt(headerMap.get("Content-Length"));
                    }
                    byte[] responseBodyBytes = new byte[responseBodyLength];
                    System.arraycopy(content, index, responseBodyBytes, 0, responseBodyLength);
                    responseBody = new String(responseBodyBytes, StandardCharsets.UTF_8);
                    log.info("responseBody:\r\n{}", formatHtml(responseBody));
                    host = host.substring(host.indexOf(".") + 1);
                    host = host.substring(0, host.lastIndexOf("."));
                    // 保存文件
                    saveData2File(host +"_" +System.currentTimeMillis(), "txt", responseBody);
                    break;
                }

                // 响应头
                String headerStr = new String(subBytes, StandardCharsets.UTF_8);
                // key : value
                int delimiter = headerStr.indexOf(SEMICOLON);
                String key = headerStr.substring(0, delimiter);
                String value = headerStr.substring(delimiter + 1).trim();
                headerMap.put(key, value);
            }
        }
    }

}
