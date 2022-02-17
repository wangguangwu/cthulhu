package com.wangguangwu.client.utils;

import java.util.HashMap;
import java.util.Map;

import static com.wangguangwu.client.entity.Commons.COM;
import static com.wangguangwu.client.entity.Symbol.*;

/**
 * @author wangguangwu
 * @date 2022/2/14 6:26 AM
 * @description 字符串工具类
 */
public class StringUtil {

    /**
     * parse host and url.
     *
     * @param url host + url
     * @return host and url
     */
    public static Map<String, String> parseHostAndUrl(String url) {
        Map<String, String> hostAndUrl = new HashMap<>(2);
        String str1 = url;
        String str2 = "";

        if (url.contains(COM)) {
            str1 = url.substring(0, url.indexOf(COM) + 4);
            str2 += url.substring(url.indexOf(COM) + 4);
        }

        hostAndUrl.put("host", str1);
        hostAndUrl.put("url", str2);
        return hostAndUrl;
    }

    /**
     * transfer map to string.
     *
     * @param map map
     * @return string
     */
    public static String map2String(Map<String, String> map) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            result.append(entry.getKey()).append(SEMICOLON).append(" ").append(entry.getValue()).append("\r\n");
        }
        return result.toString();
    }

}
