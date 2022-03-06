package com.wangguangwu.client.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Test HtmlParse util.
 *
 * @author wangguangwu
 */
public class TestHtmlParse {

    @ParameterizedTest
    @ValueSource(strings = "target/classes/export/zhipin/hangzhou/root.txt")
    public void testParseHtml(String path) {
        StringBuilder html = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String line;
            while ((line = in.readLine()) != null) {
                html.append(line);
            }
        } catch (IOException e) {
            // do something
        }
        String content = html.toString();
        HtmlParse.parseHtml(content);
    }

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
