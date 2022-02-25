package com.wangguangwu.client.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author wangguangwu
 * @date 2022/2/23
 */
public class Test {

    @ParameterizedTest
    @ValueSource(strings = "https://www.zhipin.com")
    public void test_string(String str) {
        System.out.println(str.startsWith("http:"));
        System.out.println(str.startsWith("https:"));
    }
}
