package com.wangguangwu.client.utils;

import com.wangguangwu.client.entity.Symbol;

import java.util.*;

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
            result.append(entry.getKey()).append(Symbol.SEMICOLON).append(Symbol.SPACE).append(entry.getValue()).append("\r\n");
        }
        return result.toString();
    }

    public static Map<String, String> parseQueryString(String url) {
        String queryString = url.contains(Symbol.QUESTION_MARK)
                ? url.substring(url.indexOf(Symbol.QUESTION_MARK) + 1) : url;
        Map<String, String> result = new HashMap<>();
        List<String> params = new ArrayList<>();
        if (queryString.contains(Symbol.AND)) {
            params = Arrays.asList(queryString.split(Symbol.AND));
        } else {
            params.add(queryString);
        }
        params.forEach(param -> {
            if (param.contains(Symbol.EQUALS)) {
                int index = param.indexOf(Symbol.EQUALS);
                result.put(param.substring(0, index), param.substring(index + 1));
            }
        });
        return result;
    }
}
