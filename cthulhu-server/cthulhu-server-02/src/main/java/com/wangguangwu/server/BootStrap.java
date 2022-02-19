package com.wangguangwu.server;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Startup class.
 *
 * @author wangguangwu
 */
@Slf4j
@Getter
@Setter
public class BootStrap {

    /**
     * the port number of the socket is listening on.
     */
    private int port = 8080;

    /**
     * localHost.
     */
    private String localHost = "127.0.0.1";

    /**
     * start server.
     *
     * @param args args
     */
    public static void main(String[] args) {
        BootStrap bootStrap = new BootStrap();
        try {
            // start server
            bootStrap.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * create a serverSocket bind with localHost and listen on port.
     * create request and response objects.
     * return html static resource files.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void start() throws IOException {
        // create a serverSocket
        ServerSocket serverSocket =
                new ServerSocket(port, 1, InetAddress.getByName(localHost));
        log.info("Cthulhu-server start on port: {}", port);

        while (true) {
            // get client socket
            Socket socket = serverSocket.accept();

            // create a request to parse socketInputStream
            Request request = new Request(socket.getInputStream());
            // request from google
            if ("/favicon.ico".equals(request.getUrl())) {
                continue;
            }

            // create a response to write http response to socketOutputStream
            Response response = new Response(socket.getOutputStream());
            // write html to response
            response.outputHtml(request.getUrl());
            // close socket
            socket.close();
        }
    }

}
