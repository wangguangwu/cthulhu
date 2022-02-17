package com.wangguangwu.server.servlet.impl;

import com.wangguangwu.server.Request;
import com.wangguangwu.server.Response;
import com.wangguangwu.server.servlet.Servlet;

import static com.wangguangwu.entity.Commons.GET;
import static com.wangguangwu.entity.Commons.POST;

/**
 * @author wangguangwu
 * @date 2022/2/6 12:05 AM
 * @description HttpServlet 抽象类
 */
public abstract class HttpServlet implements Servlet {

    /**
     * GET 请求调用的方法
     *
     * @param request  request 请求
     * @param response response 响应
     */
    public abstract void doGet(Request request, Response response);

    /**
     * POST 请求调用的方法
     *
     * @param request  request 请求
     * @param response response 响应
     */
    public abstract void doPost(Request request, Response response);

    @Override
    public void service(Request request, Response response) {
        if (GET.equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        }
        if (POST.equalsIgnoreCase(request.getMethod())) {
            doPost(request, response);
        }
    }
}
