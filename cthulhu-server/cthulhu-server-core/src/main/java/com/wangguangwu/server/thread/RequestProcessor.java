package com.wangguangwu.server.thread;

import com.wangguangwu.server.entity.Commons;
import com.wangguangwu.server.http.Request;
import com.wangguangwu.server.http.Response;
import jakarta.servlet.http.HttpServlet;
import lombok.extern.slf4j.Slf4j;

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
            // parse http request and send http response
            Request request = new Request(socket.getInputStream());
            Response response = new Response(socket.getOutputStream());

            String url = request.getUrl();

            if (servletMap.get(url) != null) {
                // dynamic resource handling
                HttpServlet httpServlet = servletMap.get("/");
                httpServlet.service(request, response);
            }
            // static resource handling
            response.outputHtml(url);

            // close the server
            if (Commons.SHUTDOWN_COMMAND.equalsIgnoreCase(url)) {
                System.exit(1);
            }

            socket.close();
        } catch (Exception e) {
            log.error("RequestProcessor run error: ", e);
        }
    }

}
