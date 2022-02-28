package com.wangguangwu.server.startup;

import com.wangguangwu.server.thread.RequestProcessor;
import com.wangguangwu.server.util.YamlParseUtil;
import jakarta.servlet.http.HttpServlet;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

/**
 * Start a server to listen on a port and respond to request.
 *
 * @author wangguangwu
 */
@Slf4j
@Getter
@Setter
public class BootStrap {

    /**
     * the port which the serverSocket is listen on.
     */
    private int port = 8080;

    /**
     * the host which the serverSocket is binding with.
     */
    private String host = "127.0.0.1";

    /**
     * backlog.
     */
    private int backlog = 1;

    /**
     * a map that saves the mapping between url and the corresponding servlet.
     */
    private Map<String, HttpServlet> servletMap = new HashMap<>();

    /**
     * project boot entrance.
     *
     * @param args args
     */
    public static void main(String[] args) {
        BootStrap bootStrap = new BootStrap();
        try {
            // start a webserver
            bootStrap.start();
        } catch (Exception e) {
            log.error("Start cthulhu error ", e);
        }
    }

    /**
     * start a web server and produce service.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void start() throws Exception {

        loadProperties();

        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

        // create a serverSocket
        ServerSocket serverSocket =
                new ServerSocket(port, backlog, InetAddress.getByName(host));
        log.info("server start on host: {}, port: {}", host, port);

        // listen and handle request
        while (true) {
            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket, servletMap);
            threadPoolExecutor.execute(requestProcessor);
        }
    }

    /**
     * read configuration from application.yml file.
     */
    private void loadProperties() {
        port = YamlParseUtil.INSTANCE.getValueByKey("server.port") == null
                ? port : (int) YamlParseUtil.INSTANCE.getValueByKey("server.port");

        host = YamlParseUtil.INSTANCE.getValueByKey("server.host") == null
                ? host : (String) YamlParseUtil.INSTANCE.getValueByKey("server.host");

        backlog = YamlParseUtil.INSTANCE.getValueByKey("server.backlog") == null
                ? backlog : (int) YamlParseUtil.INSTANCE.getValueByKey("server.backlog");
    }

    /**
     * save the mapping between urlPattern and servlet.
     *
     * @param urlPattern urlPattern
     * @param servlet    servlet
     */
    public void registerServlet(String urlPattern, HttpServlet servlet) {
        servletMap.put(urlPattern, servlet);
    }


    /**
     * create a threadPoolExecutor.
     *
     * @return threadPoolExecutor
     */
    private ThreadPoolExecutor getThreadPoolExecutor() {
        // core parameters
        int corePoolSize = 10;
        int maximumPoolSize = 50;
        long keepAliveTime = 100L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        return new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler
        );
    }

}
