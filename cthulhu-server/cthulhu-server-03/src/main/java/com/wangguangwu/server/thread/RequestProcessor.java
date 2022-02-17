package com.wangguangwu.server.thread;

import com.wangguangwu.server.Request;
import com.wangguangwu.server.Response;
import com.wangguangwu.server.servlet.impl.HttpServlet;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

/**
 * @author wangguangwu
 * @date 2022/2/6 3:48 PM
 * @description 请求处理器
 */
public class RequestProcessor extends Thread {

    private final Socket socket;

    private final Map<String, HttpServlet> servletMap;

    public RequestProcessor(Socket socket, Map<String, HttpServlet> servletMap) {
        this.socket = socket;
        this.servletMap = servletMap;
    }


    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();

            // 封装 Request 对象和 Response 对象
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());

            if (servletMap.get(request.getUrl()) == null) {
                // 静态资源处理
                response.outputHtml(request.getUrl());
            } else {
                // 动态资源处理
                HttpServlet httpServlet = servletMap.get(request.getUrl());
                httpServlet.service(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
