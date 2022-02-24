package com.wangguangwu.client.utils;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.wangguangwu.client.utils.StringUtil.map2String;

/**
 * StringUtil test class.
 *
 * @author wangguangwu
 * @date 2022/2/14
 */
public class Test_StringUtil {


    @Test
    public void test_Map2String() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("1", "2");
        headerMap.put("2", "2");
        headerMap.put("3", "2");
        System.out.println(map2String(headerMap));
    }

}
