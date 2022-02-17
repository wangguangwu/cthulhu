package com.wangguangwu.client.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.wangguangwu.client.utils.StringUtil.parseHostAndUrl;
import static com.wangguangwu.client.utils.StringUtil.map2String;

/**
 * StringUtil test class.
 *
 * @author wangguangwu
 * @date 2022/2/14
 */
public class Test_StringUtil {

    @ParameterizedTest
    @ValueSource(strings = "www.baidu.com?client=1")
    public void test_parseHostAndUrl(String url) {
        Map<String, String> responseMap = parseHostAndUrl(url);
        Stream.of(responseMap).forEach(System.out::println);
    }

    @Test
    public void test_Map2String() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("1", "2");
        headerMap.put("2", "2");
        headerMap.put("3", "2");
        System.out.println(map2String(headerMap));
    }

}
