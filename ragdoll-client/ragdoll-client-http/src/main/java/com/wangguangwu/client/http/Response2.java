package com.wangguangwu.client.http;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.wangguangwu.client.entity.Commons.CONTENT_LENGTH;
import static com.wangguangwu.client.entity.Symbol.SEMICOLON;
import static com.wangguangwu.client.entity.Symbol.SPACE;
import static com.wangguangwu.client.utils.StringUtil.map2String;

/**
 * @author wangguangwu
 * @date 2022/2/16
 */
@Slf4j
@Getter
@Setter
public class Response2 {

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
    private final Map<String, String> headerMap = new HashMap<>();

    /**
     * response body
     */
    private StringBuilder responseBody = new StringBuilder();

    /**
     * the value to the key Content-Length
     */
    private int responseBodyLength;

    public Response2(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * parse response.
     */
    public void parse() {

        boolean parseResponseLine = true;

        boolean parseResponseHeader = true;

        boolean parseResponseEmptyLine = true;

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                // response line
                if (parseResponseLine) {
                    // ex: HTTP/1.1 200 OK
                    String[] responseLine = line.split(SPACE);
                    protocol = responseLine[0];
                    code = Integer.parseInt(responseLine[1]);
                    description = responseLine[2];
                    log.info("protocol: {}, code; {}, description: {}", protocol, code, description);
                    parseResponseLine = false;
                    continue;
                }

                // response header
                if (line.contains(SEMICOLON) && parseResponseHeader) {
                    // key: value
                    int delimiter = line.indexOf(SEMICOLON);
                    String key = line.substring(0, delimiter);
                    String value = line.substring(delimiter + 1).trim();
                    headerMap.put(key, value);
                    continue;
                }

                // response empty line
                if ("".equals(line) && parseResponseEmptyLine) {
                    parseResponseHeader = false;
                    parseResponseEmptyLine = false;
                    // print response header
                    log.info("response header: {}", map2String(headerMap));
                    continue;
                }

                // response body
                // get response body length
                if (headerMap.containsKey(CONTENT_LENGTH)) {
                    responseBodyLength = Integer.parseInt(headerMap.get(CONTENT_LENGTH));
                }
                responseBody.append(line);
                log.info("line: {}", line);
                if (responseBody.toString().getBytes(StandardCharsets.UTF_8).length == responseBodyLength) {
                    log.info("response body: \r\n{}", responseBody);
                    break;
                }
            }
        } catch (IOException e) {
            log.error("Response2 parse error: \r\n", e);
        }
    }
}
