package com.wangguangwu.client.service.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.URL;


/**
 * @author wangguangwu
 * @date 2022/2/27
 */
public class TestWorkImpl {

    @ParameterizedTest
    @ValueSource(strings = {"https://www.baidu.top/index.html"})
    public void test_DomainName(String url) {
        try {
            URL urlObject = new URL(url);
            System.out.println("=================");
            System.out.println(urlObject.getProtocol());
            System.out.println(urlObject.getHost());
            System.out.println(urlObject.getPort());
            System.out.println(urlObject.getDefaultPort());
            System.out.println(urlObject.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
