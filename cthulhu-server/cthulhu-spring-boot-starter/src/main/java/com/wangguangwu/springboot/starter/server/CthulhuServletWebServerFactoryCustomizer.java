package com.wangguangwu.springboot.starter.server;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;

/**
 * @author yuanzhixiang
 */
public class CthulhuServletWebServerFactoryCustomizer implements
    WebServerFactoryCustomizer<CthulhuServletWebServerFactory> {

    @Override
    public void customize(CthulhuServletWebServerFactory factory) {
        System.out.println();
    }
}
