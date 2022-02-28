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
//    public static Map<String, String> parseHostAndUrl(String redirectLocation, int port) {
//        String protocol = port == 80 ? HTTP_PROTOCOL : HTTPS_PROTOCOL;
//        String fullUrl = constructUrl(redirectLocation, protocol);
//        return parseHostAndUrl(fullUrl);
//    }




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
