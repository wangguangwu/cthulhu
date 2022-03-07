package com.wangguangwu.client.utils;

import org.junit.jupiter.api.Test;

/**
 * Test HtmlParse util.
 *
 * @author wangguangwu
 */
public class TestHtmlParse {


    @Test
    public void test() {
        String a = "HTTP/1.1 302 Found\n" +
                "Date: Sun, 06 Mar 2022 14:16:52 GMT\n" +
                "Content-Type: text/plain;charset=UTF-8\n" +
                "Content-Length: 0\n" +
                "Connection: keep-alive\n" +
                "Set-Cookie: acw_tc=0a099d9216465762123616218e9e12b2824ec5f03b125cf7dc499bc6b1f882;path=/;HttpOnly;Max-Age=1800\n" +
                "location: /web/common/security-check.html?seed=YuNGpT4o5EV0Zyy6DwYK4CGlZk1OA7ALExbqgkAWtm4%3D&name=3dc7c73c&ts=1646576212376&callbackUrl=%2Fjob_detail%2F%3Fquery%3Djava%26city%3D101210100%26industry%3D%26position%3D&srcReferer=\n" +
                "process-stage: Stage-Outbound\n" +
                "Cache-Control: no-cache\n" +
                "\n" +
                "HelloWorld\n";
        final String[] strings = a.split("\r\n");
        for (String string : strings) {
            System.out.println(string);
        }
    }

}
