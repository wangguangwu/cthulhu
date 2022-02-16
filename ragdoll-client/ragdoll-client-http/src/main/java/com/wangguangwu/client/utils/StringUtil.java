package com.wangguangwu.client.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wangguangwu.client.entity.Symbol.*;

/**
 * @author wangguangwu
 * @date 2022/2/14 6:26 AM
 * @description 字符串工具类
 */
public class StringUtil {

    /**
     * 解析出 host 和 url
     *
     * @param url 访问站点 + 路径
     * @return host 和 url
     */
    public static Map<String, String> parseHostAndUrl(String url) {
        Map<String, String> hostAndUrl = new HashMap<>(2);
        String str1 = url;
        String str2 = "/";

        if (url.contains(".com")) {
            str1 = url.substring(0, url.indexOf(".com") + 4);
            str2 += url.substring(url.indexOf(".com") + 4);
        }

        hostAndUrl.put("host", str1);
        hostAndUrl.put("url", str2);
        return hostAndUrl;
    }

    /**
     * 将 map 转化为 string
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
