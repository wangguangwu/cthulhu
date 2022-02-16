package com.wangguangwu.client.http;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.wangguangwu.client.entity.Commons.CONTENT_LENGTH;
import static com.wangguangwu.client.entity.Symbol.*;
import static com.wangguangwu.client.entity.Symbol.SEMICOLON;
import static com.wangguangwu.client.utils.StreamUtil.parseInputStream2bytes;
import static com.wangguangwu.client.utils.StringUtil.map2String;

/**
 * Use a large array, such as 15360 length, to receive all data (even the data can not fill up this array)
 * then parse the data.
 *
 * @author wangguangwu
 * @date 2022/2/16
 */
@Slf4j
@Getter
@Setter
public class Response1 {

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
    private String responseBody;

    /**
     * the value to the key Content-Length
     */
    private int responseBodyLength;

    public Response1(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * parse response.
     */
    public void parse() {
        // All data
        byte[] content = parseInputStream2bytes(inputStream, 15360);

        // whether to parse the request line
        boolean parseResponseLine = true;

        // loop to parse response
        for (int index = 0, i = 0; i < Objects.requireNonNull(content).length - 1; i++) {
            // each line end up with '\r\n'
            if (content[i] == RETURN && content[i + 1] == WRAP) {
                // response line
                if (parseResponseLine) {
                    // line length
                    int length = i - index;
                    // create a new buffer array
                    byte[] subBytes = new byte[length];
                    System.arraycopy(content, index, subBytes, 0, length);
                    // response line
                    String[] responseLine = new String(subBytes, StandardCharsets.UTF_8).split(SPACE);

                    protocol = responseLine[0];
                    code = Integer.parseInt(responseLine[1]);
                    description = responseLine[2];

                    log.info("protocol: {}, code: {}, description: {}", protocol, code, description);

                    parseResponseLine = false;
                    index = i + 2;
                    continue;
                }
                // parse response header
                // line length
                int length = i - index;
                // create a new buffer array
                byte[] subBytes = new byte[length];

                // copy data to new array
                System.arraycopy(content, index, subBytes, 0, length);

                // next line
                index = i + 2;

                // response empty line
                if (subBytes.length == 0) {
                    log.info("responseHeader: {}", map2String(headerMap));
                    parseResponseBody(content, index);
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

    }

    /**
     * get the value of response body length from the special response header: Content-Length.
     * copy data from srcBytes and transfer to String type.
     *
     * @param content srcBytes
     * @param index   fromIndex
     */
    private void parseResponseBody(byte[] content, int index) {
        if (headerMap.containsKey(CONTENT_LENGTH)) {
            // response body length
            responseBodyLength = Integer.parseInt(headerMap.get(CONTENT_LENGTH));
            byte[] subBytes = new byte[responseBodyLength];
            System.arraycopy(content, index, subBytes, 0, responseBodyLength);
            // response body
            responseBody = new String(subBytes, StandardCharsets.UTF_8);
            log.info("responseBody: \r\n{}", responseBody);
        }
    }

}
