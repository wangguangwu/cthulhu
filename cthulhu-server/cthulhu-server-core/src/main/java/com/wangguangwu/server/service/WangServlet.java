package com.wangguangwu.server.service;

import com.wangguangwu.server.http.Response;
import com.wangguangwu.server.util.HttpProtocolUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Test servlet.
 *
 * @author wangguangwu
 */
@Slf4j
public class WangServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) {
        log.info("WangServlet init ...");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws IOException {
        String content = "<h1>wangServlet response</h1>";
        Response response = (Response) res;
        response.output(
                HttpProtocolUtil.getHttp200(content.getBytes().length) + content);
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        log.info("WangServlet destroy ...");
    }

}
