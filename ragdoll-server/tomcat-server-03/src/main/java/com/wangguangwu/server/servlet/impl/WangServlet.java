package com.wangguangwu.server.servlet.impl;

import com.wangguangwu.server.Request;
import com.wangguangwu.server.Response;
import com.wangguangwu.util.HttpProtocolUtil;

import java.io.IOException;

/**
 * @author wangguangwu
 * @date 2022/2/6 12:15 AM
 * @description 业务类 servlet
 */
public class WangServlet extends HttpServlet {

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doGet(Request request, Response response) {

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String content = "<h1>Servlet get</h1>";
        try {
            response.output(
                    HttpProtocolUtil.getHttp200(content.getBytes().length) + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        String content = "<h1>Servlet post</h1>";
        try {
            response.output(
                    HttpProtocolUtil.getHttp200(content.getBytes().length) + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
