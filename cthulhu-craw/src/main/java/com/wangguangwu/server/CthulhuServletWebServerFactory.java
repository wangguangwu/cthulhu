package com.wangguangwu.server;

import jakarta.servlet.ServletException;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory;

/**
 * @author yuanzhixiang
 */
public class CthulhuServletWebServerFactory extends AbstractServletWebServerFactory {

    @Override
    public WebServer getWebServer(ServletContextInitializer... initializers) {
        CthulhuServletContext servletContext = new CthulhuServletContext("",null);

        if (initializers != null) {
            for (ServletContextInitializer initializer : initializers) {
                try {
                    initializer.onStartup(servletContext);
                } catch (ServletException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return new CthulhuWebServer();
    }
}
