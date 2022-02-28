package com.wangguanwgu.server.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static com.wangguangwu.server.util.ParseRequestUtil.parseQueryString;

/**
 * @author wangguangwu
 */
public class ParseRequestUtil {

    @ParameterizedTest
    @ValueSource(strings = "/cthulhu/helloCthulhu?name=cthulhu&age=20")
    public void test_parseQueryString(String url) {
        Map<String, String> params = parseQueryString(url);
        params.forEach((k, v) -> System.out.println(k + ":" + v));
    }

}
