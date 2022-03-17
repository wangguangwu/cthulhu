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
import java.util.Objects;

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
     * parse and construct response.
     *
     * @return response
     */
    public Response parseResponse() {

        Response response = new Response();

        try {
            // response line and header and empty line
            byte[] firstRead = parseInputStream2Bytes(Commons.DEFAULT_BUFFER_SIZE);

            // get responseBody remain content
            byte[] remainContent = parseResponseBesidesBody(firstRead);
            response.setProtocol(protocol);
            response.setCode(code);
            response.setDescription(description);
            response.setHeaderMap(headerMap);

            int remainLength = Objects.requireNonNull(remainContent).length;
            int responseBesidesBodyLength = Commons.DEFAULT_BUFFER_SIZE - remainLength;

            byte[] responseBodyContent = new byte[0];
            String charset = getCharset();

            if (firstRead.length < Commons.DEFAULT_BUFFER_SIZE) {
                // 说明读到结尾，不会继续往下读取
                responseBodyContent = new byte[remainLength];
                System.arraycopy(firstRead, responseBesidesBodyLength, responseBodyContent, 0, remainLength);
                response.setResponseBody(new String(responseBodyContent, charset));
                return response;
            }

            // CONTENT_LENGTH
            int responseBodyLength = headerMap.containsKey(CONTENT_LENGTH)
                    ? Integer.parseInt(headerMap.get(CONTENT_LENGTH)) : 30720;

            // response body content
            int responseBodyRemainLength = responseBodyLength - remainLength;
            if (responseBodyRemainLength > 0) {
                responseBodyContent = parseInputStream2Bytes(responseBodyRemainLength);
            }

            responseBody = new String(remainContent, charset) + new String(responseBodyContent, charset);
            response.setResponseBody(responseBody);
        } catch (Exception e) {
            log.error("parse response error", e);
        }
        return response;
    }

    private String getCharset() {
        String charset = Commons.DEFAULT_CHARSET.name();
        if (headerMap.containsKey(Http.CONTENT_TYPE)
                && headerMap.get(Http.CONTENT_TYPE).contains(Http.CHARSET)) {
            String contentValue = headerMap.get(Http.CONTENT_TYPE);
            int charsetIndex = contentValue.indexOf(Http.CHARSET);
            charset = contentValue.substring(charsetIndex + 8);
        }
        return charset;
    }

    private void parseResponseLine(String responseLine) {
        String[] responseLineSpilt = responseLine.split(SPACE);
        protocol = responseLineSpilt[0];
        code = Integer.parseInt(responseLineSpilt[1]);
        description = responseLineSpilt[2];
        // 404 NOT FOUND
        if (Http.BAD_RESPONSE.contains(code)) {
            throw new RuntimeException("error response");
        }
    }

    private byte[] parseResponseBesidesBody(byte[] content) {

        boolean parseResponseLine = true;
        byte[] remainContent;
        int responseBesidesBodyLength;
        int remainingLength;

        for (int index = 0, i = 0; i < content.length - 1; i++) {
            // each line is end with "\r\n"
            if (content[i] == Symbol.RETURN && content[i + 1] == Symbol.WRAP) {
                // get every line
                int length = i - index;
                byte[] subBytes = new byte[length];
                System.arraycopy(content, index, subBytes, 0, length);
                String line = new String(subBytes, StandardCharsets.UTF_8);
                // next line
                index = i + 2;

                // parse response line
                if (parseResponseLine) {
                    parseResponseLine(line);
                    parseResponseLine = false;
                    continue;
                }
                // parse response header
                // response empty line
                if (subBytes.length == 0) {
                    responseBesidesBodyLength = index;
                    remainingLength = content.length - responseBesidesBodyLength;
                    remainContent = new byte[remainingLength];
                    // copy data
                    System.arraycopy(content, index, remainContent, 0, remainingLength);
                    return remainContent;
                }
                // response header
                int delimiter = line.indexOf(SEMICOLON);
                String key = line.substring(0, delimiter);
                String value = line.substring(delimiter + 1).trim();
                headerMap.put(key, value);
            }
        }
        return null;
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

                if (readCount > 0 &&
                        new String(bytes, 0, readCount).contains("Content-Length: 0")) {
                    break;
                }
                readCount += inputStream.read(bytes, readCount, capacity - readCount);
                if (oldReadCount > readCount) {
                    break;
                }
            }
        } catch (IOException e) {
            log.error("Response parseInputStream2Bytes error", e);
        }
        byte[] content = new byte[readCount];
        System.arraycopy(bytes, 0, content, 0, readCount);
        return content;
    }

}
