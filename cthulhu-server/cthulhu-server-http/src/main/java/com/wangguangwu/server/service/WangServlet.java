package com.wangguangwu.server.service;

import com.wangguangwu.server.http.Response;
import com.wangguangwu.util.HttpProtocolUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * Test servlet.
 *
 * @author wangguangwu
 */
@Slf4j
public class WangServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        log.info("WangServlet init ...");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
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
