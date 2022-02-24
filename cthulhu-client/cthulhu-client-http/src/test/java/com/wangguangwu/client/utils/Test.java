package com.wangguangwu.client.utils;

import org.springframework.util.LinkedMultiValueMap;

import java.util.List;
import java.util.Objects;

/**
 * @author wangguangwu
 * @date 2022/2/23
 */
public class Test {

    @org.junit.jupiter.api.Test
    public void test_multiValueMap() {
        var param = new LinkedMultiValueMap<String, String>();
        param.add("phone", "111");
        param.add("phone", "222");
        param.add("temp_id", "temp_id1");
        param.add("param", "param");
        param.add("param", "param2");
        param.add("temp_id", "temp_id2");
        param.add("phone", "333");
        param.add("param", "param3");

        System.out.println("=============================");

        for (String key : param.keySet()) {

            List<String> values = param.get(key);
            System.out.println("key: " + key);

            for (String value : Objects.requireNonNull(values)) {
                System.out.println("value: " + value);
            }
        }
    }

    @org.junit.jupiter.api.Test
    public void test_string() {
        String a = "https://www.zhipin.com";
        System.out.println(a.startsWith("http:"));
        System.out.println(a.startsWith("https:"));
    }
}
