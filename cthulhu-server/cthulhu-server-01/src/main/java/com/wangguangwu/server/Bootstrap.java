package com.wangguangwu.server;

import com.wangguangwu.util.HttpProtocolUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
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
public class Bootstrap {

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
        Bootstrap bootStrap = new Bootstrap();
        try {
            // start server
            bootStrap.start();
        } catch (IOException e) {
            log.error("BootStrap main error: \r\n", e);
        }
    }

    /**
     * create a serverSocket bind with localHost and listen on port.
     * when the browser requests http://localhost:8080, the server returns "Hello World" to the page.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void start() throws IOException {
        // create a serverSocket and listen on to the local port
        ServerSocket serverSocket
                = new ServerSocket(port, 1, InetAddress.getByName(localHost));
        log.info("Cthulhu-server start on port: {}", port);

        while (true) {
            // get a client socket
            // accept is a blocking method
            Socket socket = serverSocket.accept();
            // get outputStream
            OutputStream outputStream = socket.getOutputStream();
            // response data
            String data = "<h1>Hello World</h1>";
            // http 200 response
            String responseText =
                    HttpProtocolUtil.getHttp200(data.getBytes().length) + data;
            // write http response to outputStream
            outputStream.write(responseText.getBytes());
            // close client socket
            socket.close();
        }
    }

}

