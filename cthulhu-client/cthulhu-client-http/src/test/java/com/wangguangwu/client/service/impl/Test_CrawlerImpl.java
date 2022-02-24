package com.wangguangwu.client.service.impl;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.wangguangwu.client.entity.Commons.COM;
import static com.wangguangwu.client.entity.Http.*;
import static com.wangguangwu.client.entity.Symbol.QUESTION_MARK;
import static com.wangguangwu.client.entity.Symbol.SLASH;

/**
 * Test CrawlerImpl.
 *
 * @author wangguangwu
 * @date 2022/2/23
 */
@Getter
@Setter
public class Test_CrawlerImpl {

    private static String host;

    private static String url;

    private static int port;

    private static String protocol = "";


    @ParameterizedTest
    @ValueSource(strings = "https://www.baidu.com?a.txt")
    public void test_parseHostAndUrl(String str) {

        if (str.startsWith(HTTPS_PROTOCOL_START)) {
            protocol = HTTPS_PROTOCOL;
            port = 443;
        } else {
            protocol = HTTP_PROTOCOL;
            port = 80;
        }

        if (str.contains("//")) {
            str = str.substring(str.indexOf("//") + 2);
        }

        host = str.contains(COM) ? str.substring(0, str.indexOf(COM) + 4) : str;
        url = str.contains(COM) ? str.substring(str.indexOf(COM) + 4) : "";

        url = url.startsWith(SLASH) || url.startsWith(QUESTION_MARK) ? url : SLASH + url;

        System.out.println(host);
        System.out.println(port);
        System.out.println(url);
        System.out.println(protocol);
    }

}
