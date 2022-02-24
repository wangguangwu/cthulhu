package com.wangguangwu.client.utils;

import java.util.HashMap;
import java.util.Map;

import static com.wangguangwu.client.entity.Commons.COM;
import static com.wangguangwu.client.entity.Http.*;
import static com.wangguangwu.client.entity.Symbol.*;

/**
 * some methods to operate string.
 *
 * @author wangguangwu
 */
public class StringUtil {

    /**
     * transfer map to string.
     *
     * @param map map
     * @return string
     */
    public static String map2String(Map<String, String> map) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            result.append(entry.getKey()).append(SEMICOLON).append(SPACE).append(entry.getValue()).append("\r\n");
        }
        return result.toString();
    }

    /**
     * parse host、url、protocol and port.
     *
     * @param redirectLocation redirectLocation
     * @param port             port
     */
    public static Map<String, String> parseHostAndUrl(String redirectLocation, int port) {
        String protocol = port == 80 ? HTTP_PROTOCOL : HTTPS_PROTOCOL;
        String fullUrl = constructUrl(redirectLocation, protocol);
        return parseHostAndUrl(fullUrl);
    }

    /**
     * parse host、url、protocol and port.
     *
     * @param fullUrl host + url, such as https://www.baidu.com/index.html
     */
    public static Map<String, String> parseHostAndUrl(String fullUrl) {
        Map<String, String> map = new HashMap<>(4);

        String protocol;
        String host;
        String url;
        String port;

        if (fullUrl.startsWith(HTTPS_PROTOCOL_START)) {
            protocol = HTTPS_PROTOCOL;
            port = "443";
        } else {
            protocol = HTTP_PROTOCOL;
            port = "80";
        }

        if (fullUrl.contains(DOUBLE_SLASH)) {
            fullUrl = fullUrl.substring(fullUrl.indexOf(DOUBLE_SLASH) + 2);
        }

        host = fullUrl.contains(COM) ? fullUrl.substring(0, fullUrl.indexOf(COM) + 4) : fullUrl;
        url = fullUrl.contains(COM) ? fullUrl.substring(fullUrl.indexOf(COM) + 4) : "";

        url = url.startsWith(SLASH) ? url : SLASH + url;

        map.put("protocol", protocol);
        map.put("host", host);
        map.put("url", url);
        map.put("port", port);
        return map;
    }


    /**
     * construct full url.
     *
     * @param redirectLocation redirectLocation
     * @param protocol         protocol
     * @return fullUrl
     */
    private static String constructUrl(String redirectLocation, String protocol) {
        if (redirectLocation.startsWith(SLASH)) {
            if (redirectLocation.startsWith(DOUBLE_SLASH)) {
                return protocol + SEMICOLON + redirectLocation;
            }
            return protocol + SEMICOLON + SLASH + redirectLocation;
        }
        return protocol + SEMICOLON + DOUBLE_SLASH + redirectLocation;
    }

}
