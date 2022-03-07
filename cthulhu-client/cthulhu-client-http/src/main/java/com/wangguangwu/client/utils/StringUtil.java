package com.wangguangwu.client.utils;

import com.wangguangwu.client.entity.Symbol;

import java.util.Map;

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

}
