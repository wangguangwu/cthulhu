package com.wangguangwu.springboot.starter.server;

import com.wangguangwu.server.startup.BootStrap;
import org.springframework.boot.web.server.GracefulShutdownCallback;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.server.WebServerException;

/**
 * @author yuanzhixiang
 */
public class CthulhuWebServer implements WebServer {

    private final BootStrap bootStrap;

    public CthulhuWebServer(BootStrap bootStrap) {
        this.bootStrap = bootStrap;
    }

    @Override
    public void start() throws WebServerException {
        try {
            bootStrap.start();
        } catch (Exception e) {
            throw new WebServerException("Failed to start web server", e);
        }
    }

    @Override
    public void stop() throws WebServerException {

    }

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public void shutDownGracefully(GracefulShutdownCallback callback) {
        WebServer.super.shutDownGracefully(callback);
    }
}
