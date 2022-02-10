package com.wangguangwu.server.servlet;

import com.wangguangwu.server.Request;
import com.wangguangwu.server.Response;

/**
 * @author wangguangwu
 * @date 2022/2/5 12:03 PM
 * @description servlet 规范
 */
public interface Servlet {

    /**
     * 初始化方法
     *
     * @throws Exception Exception
     */
    void init() throws Exception;

    /**
     * 销毁方法
     *
     * @throws Exception Exception
     */
    void destroy() throws Exception;

    /**
     * 主要的服务方法
     *
     * @param request  request 请求
     * @param response response 响应
     * @throws Exception Exception
     */
    void service(Request request, Response response) throws Exception;

}
