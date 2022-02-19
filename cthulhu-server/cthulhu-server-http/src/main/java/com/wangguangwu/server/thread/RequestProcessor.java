package com.wangguangwu.server.thread;

import com.wangguangwu.server.http.Request;
import com.wangguangwu.server.http.Response;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import java.net.Socket;
import java.util.Map;

/**
 * Parse http request.
 *
 * @author wangguangwu
 */
@Slf4j
public class RequestProcessor extends Thread {

    /**
     * socket.
     */
    private final Socket socket;

    /**
     * a map that saves the mapping between url and the corresponding servlet.
     */
    private final Map<String, HttpServlet> servletMap;

    /**
     * socket and servletMap constructor.
     *
     * @param socket     socket
     * @param servletMap servletMap
     */
    public RequestProcessor(Socket socket, Map<String, HttpServlet> servletMap) {
        this.socket = socket;
        this.servletMap = servletMap;
    }


    @Override
    public void run() {
        try {
            // parse http request and http response
            Request request = new Request(socket.getInputStream());
            Response response = new Response(socket.getOutputStream());

            if (servletMap.get(request.getUrl()) == null) {
                // static resource handling
                response.outputHtml(request.getUrl());
            } else {
                // dynamic resource handling
                HttpServlet httpServlet = servletMap.get(request.getUrl());
                httpServlet.service(request, response);
            }
        } catch (Exception e) {
            log.error("RequestProcessor run error: \r\n", e);
        }
    }
}
