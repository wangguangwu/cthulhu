package com.wangguangwu.client.http;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.wangguangwu.client.entity.Commons.CONTENT_LENGTH;
import static com.wangguangwu.client.entity.Symbol.*;
import static com.wangguangwu.client.utils.FileUtil.saveData2File;
import static com.wangguangwu.client.utils.StringUtil.map2String;

/**
 * use a little array, such as 1024 bytes size
 * to receive response line and header, maybe has a little response body
 * parse the little array to get the response body length
 * set a larger array to receive the response body (the capacity we will need to calculate).
 *
 * @author wangguangwu
 * @date 2022/2/16
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class Response {

    /**
     * inputStream
     */
    private InputStream inputStream;

    /**
     * save file name
     */
    private String fileName;

    /**
     * protocol
     */
    private String protocol;

    /**
     * status code
     */
    private int code;

    /**
     * status description
     */
    private String description;

    /**
     * response header
     */
    private final Map<String, String> headerMap = new HashMap<>();

    /**
     * whole response
     */
    private String wholeResponse;

    /**
     * response line
     */
    private String responseLine;

    /**
     * response header
     */
    private String responseHeader;

    /**
     * response empty Line
     */
    private String responseEmptyLine = "\r\n";

    /**
     * response body
     */
    private String responseBody;

    /**
     * the value to the key Content-Length
     */
    private int responseBodyLength;

    /**
     * length of response line and header and emptyLine
     */
    private int responseBesidesBodyLength;

    /**
     * length of the firstRead's part of response body
     */
    private int remainingLength;


    /**
     * inputStream constructor
     *
     * @param inputStream inputStream
     */
    public Response(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * parse the response.
     */
    public void parse() {

        // response line and header and empty line
        byte[] firstRead = parseInputStream2Bytes(1024);

        // whether the responseLine has been readed
        boolean parseResponseLine = true;

        // in the readFirst but belongs to response body
        byte[] remainContent = new byte[0];

        // loop to parse the response
        for (int index = 0, i = 0; i < firstRead.length - 1; i++) {
            // each line is end with "\r\n"
            if (firstRead[i] == RETURN && firstRead[i + 1] == WRAP) {

                // response line
                if (parseResponseLine) {
                    // length of a line
                    int length = i - index;
                    // buffer array
                    byte[] subBytes = new byte[length];
                    // copy data
                    System.arraycopy(firstRead, index, subBytes, 0, length);
                    // responseLine, such as HTTP/1.1 200 OK
                    responseLine = new String(subBytes, StandardCharsets.UTF_8);

                    // split with " "
                    String[] responseLineSpilt = responseLine.split(SPACE);
                    protocol = responseLineSpilt[0];
                    code = Integer.parseInt(responseLineSpilt[1]);
                    description = responseLineSpilt[2];

                    log.info("responseLine: {}", responseLine);
                    parseResponseLine = false;
                    // next line
                    index = i + 2;
                    continue;
                }

                // parse response header
                // length of a line
                int length = i - index;
                // create a new buffer array
                byte[] subBytes = new byte[length];

                // copy data to new array
                System.arraycopy(firstRead, index, subBytes, 0, length);

                // next line
                index = i + 2;

                // response empty line
                if (subBytes.length == 0) {
                    responseBesidesBodyLength = index;
                    remainingLength = firstRead.length - responseBesidesBodyLength;
                    remainContent = new byte[remainingLength];
                    // copy data
                    System.arraycopy(firstRead, index, remainContent, 0, remainingLength);
                    // response header
                    responseHeader = map2String(headerMap);

                    log.info("responseHeader: \r\n{}", responseHeader);
                    log.info("responseBesidesBodyLength: {}", responseBesidesBodyLength);
                    log.info("remainingLength: {}", remainingLength);
                    break;
                }

                // response header
                String headerStr = new String(subBytes, StandardCharsets.UTF_8);
                // key : value
                int delimiter = headerStr.indexOf(SEMICOLON);
                String key = headerStr.substring(0, delimiter);
                String value = headerStr.substring(delimiter + 1).trim();
                headerMap.put(key, value);
            }
        }

        // CONTENT_LENGTH
        if (headerMap.containsKey(CONTENT_LENGTH)) {
            responseBodyLength = Integer.parseInt(headerMap.get(CONTENT_LENGTH));
        }

        // response body content
        byte[] responseBodyContent = parseInputStream2Bytes(responseBodyLength - remainingLength);
        responseBody = new String(remainContent, StandardCharsets.UTF_8)
                + new String(responseBodyContent, StandardCharsets.UTF_8);
        log.info("responseBody: \r\n{}", responseBody);
        // save data to file
        wholeResponse = responseLine + responseHeader + responseEmptyLine + responseBody;
        handleFileName();
        saveData2File(fileName + "_responseBody", responseBody);
        saveData2File(fileName + "_response", wholeResponse);
    }

    /**
     * read capacity-sized data from inputStream.
     *
     * @param capacity capacity
     * @return bytes
     */
    private byte[] parseInputStream2Bytes(int capacity) {
        byte[] bytes = new byte[capacity];
        int readCount = 0;
        int oldReadCount;
        try {
            while (readCount < capacity) {
                oldReadCount = readCount;
                readCount += inputStream.read(bytes, readCount, capacity - readCount);
                // i = -1 represent the end of inputStream
                if (oldReadCount > readCount) {
                    break;
                }
            }
        } catch (IOException e) {
            log.error("Response parseInputStream2Bytes error: \r\n", e);
        }
        return bytes;
    }

    /**
     * ex: www.baidu.com ==> baidu
     */
    private void handleFileName() {
        int index1 = fileName.indexOf(POINT);
        int index2 = fileName.lastIndexOf(POINT);
        fileName = fileName.substring(index1 + 1, index2);
    }

    public static void main(String[] args) {
        String a = "Cache-Control: no-cache\n" +
                "process-stage: Stage-Outbound\n" +
                "Connection: keep-alive\n" +
                "Set-Cookie: lastCity=101210100; path=/; domain=.zhipin.com; Max-Age=31536000; Expires=Fri, 17-Feb-2023 06:26:10 GMT\n" +
                "Vary: Accept-Encoding\n" +
                "Content-Length: 392116\n" +
                "Date: Thu, 17 Feb 2022 06:26:10 GMT\n" +
                "Content-Language: zh-CN\n" +
                "Content-Type: text/html;charset=utf-8";
        System.out.println(a.getBytes(StandardCharsets.UTF_8).length);
    }
}
