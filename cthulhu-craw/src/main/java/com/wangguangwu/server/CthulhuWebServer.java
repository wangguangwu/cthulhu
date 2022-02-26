package com.wangguangwu.server;

import org.springframework.boot.web.server.GracefulShutdownCallback;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.server.WebServerException;

import com.wangguangwu.server.startup.BootStrap;

/**
 * @author yuanzhixiang
 */
public class CthulhuWebServer implements WebServer {

    private final BootStrap bootStrap = new BootStrap();

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
