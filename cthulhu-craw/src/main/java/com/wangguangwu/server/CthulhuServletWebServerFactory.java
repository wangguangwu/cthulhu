package com.wangguangwu.server;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory;

/**
 * @author yuanzhixiang
 */
public class CthulhuServletWebServerFactory extends AbstractServletWebServerFactory {

    @Override
    public WebServer getWebServer(ServletContextInitializer... initializers) {
        return new CthulhuWebServer();
    }
}
