package com.wangguangwu.client.http;

import com.alibaba.fastjson.JSON;
import com.wangguangwu.client.entity.Commons;
import com.wangguangwu.client.entity.Http;
import com.wangguangwu.client.entity.Symbol;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.wangguangwu.client.entity.Commons.CONTENT_LENGTH;
import static com.wangguangwu.client.entity.Symbol.*;

/**
 * use a little array, such as 1024 bytes size
 * to receive response line and header, maybe has a little response body
 * parse the little array to get the response body length
 * set a larger array to receive the response body (the capacity we will need to calculate).
 *
 * @author wangguangwu
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class Response {

    /**
     * response
     */
    Response response = new Response();

    /**
     * inputStream
     */
    private InputStream inputStream;

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
    private Map<String, String> headerMap = new HashMap<>();

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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    /**
     * parse the response.
     *
     * @return response
     */
    public Response parseResponse() {
        // response line and header and empty line
        byte[] firstRead = parseInputStream2Bytes(Commons.DEFAULT_BUFFER_SIZE);

        boolean parseResponseLine = true;
        byte[] remainContent = new byte[0];

        // parse response
        for (int index = 0, i = 0; i < firstRead.length - 1; i++) {
            // each line is end with "\r\n"
            if (firstRead[i] == Symbol.RETURN && firstRead[i + 1] == Symbol.WRAP) {
                // parse response line
                if (parseResponseLine) {
                    int length = i - index;
                    byte[] subBytes = new byte[length];
                    System.arraycopy(firstRead, index, subBytes, 0, length);
                    String responseLine = new String(subBytes, StandardCharsets.UTF_8);
                    String[] responseLineSpilt = responseLine.split(SPACE);
                    response.setProtocol(responseLineSpilt[0]);
                    response.setCode(Integer.parseInt(responseLineSpilt[1]));
                    response.setDescription(responseLineSpilt[2]);
                    // 404 NOT FOUND
                    if (Http.BAD_RESPONSE.contains(response.getCode())) {
                        throw new RuntimeException("error response");
                    }
                    parseResponseLine = false;
                    index = i + 2;
                    continue;
                }
                // parse response header
                int length = i - index;
                byte[] subBytes = new byte[length];
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
                    response.setHeaderMap(headerMap);
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

        byte[] responseBodyContent = new byte[0];

        if (firstRead.length < Commons.DEFAULT_BUFFER_SIZE) {
            // 说明读到结尾，不会继续往下读取
            responseBodyContent = new byte[remainingLength];
            System.arraycopy(firstRead, responseBesidesBodyLength, responseBodyContent, 0, remainingLength);
        } else {
            // CONTENT_LENGTH
            if (headerMap.containsKey(CONTENT_LENGTH)) {
                responseBodyLength = Integer.parseInt(headerMap.get(CONTENT_LENGTH));
            } else {
                // 不含有 CONTENT_LENGTH 响应头
                responseBodyLength = 30720;
            }
        }

        // response body content
        int responseBodyRemainLength = responseBodyLength - remainingLength;
        // inputStream 中还有剩余的响应体数据
        if (responseBodyRemainLength > 0) {
            responseBodyContent = parseInputStream2Bytes(responseBodyRemainLength);
        }
        String charset = Commons.DEFAULT_CHARSET.name();
        if (headerMap.containsKey(Http.CONTENT_TYPE)
                && headerMap.get(Http.CONTENT_TYPE).contains(Http.CHARSET)) {

            String contentValue = headerMap.get(Http.CONTENT_TYPE);
            int charsetIndex = contentValue.indexOf(Http.CHARSET);
            charset = contentValue.substring(charsetIndex + 8);
        }
        try {
            responseBody = new String(remainContent, charset)
                    + new String(responseBodyContent, charset);
        } catch (UnsupportedEncodingException e) {
            responseBody = new String(remainContent, Commons.DEFAULT_CHARSET)
                    + new String(responseBodyContent, Commons.DEFAULT_CHARSET);
        }
        response.setResponseBody(responseBody);
        return response;
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

                // TODO: 针对响应体不为 0，但是长度不足 1024 字节

                // 针对响应体为 0 的情况
                if (readCount > 0 &&
                        new String(bytes, 0, readCount).contains("Content-Length: 0")) {
                    break;
                }
                readCount += inputStream.read(bytes, readCount, capacity - readCount);
                // i = -1 represent the end of inputStream
                // TODO: 优化
                if (oldReadCount > readCount) {
                    break;
                }
            }
        } catch (IOException e) {
            log.error("Response parseInputStream2Bytes error: ", e);
        }
        byte[] content = new byte[readCount];
        System.arraycopy(bytes, 0, content, 0, readCount);
        return content;
    }

}
