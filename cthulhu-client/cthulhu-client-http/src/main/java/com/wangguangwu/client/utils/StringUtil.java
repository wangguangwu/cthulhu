package com.wangguangwu.client.utils;

import com.wangguangwu.client.entity.Symbol;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * some methods to operate string.
 *
 * @author wangguangwu
 */
public class StringUtil {

    private static final String MATCH = "[0-9]";

    /**
     * parseQueryString
     *
     * @param url url
     * @return queryString
     */
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

    /**
     * 查询字符串中首个数字出现的位置
     *
     * @param str 查询的字符串
     * @return 若存在，返回位置索引，否则返回-1；
     */
    public static int findFirstIndexNumberOfStr(String str) {
        //	正则表达式
        Pattern pattern = Pattern.compile(MATCH);
        int i = -1;
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            i = matcher.start();
        }
        return i;
    }
}
