package com.wangguangwu.server.startup;

import com.wangguangwu.server.thread.RequestProcessor;
import jakarta.servlet.http.HttpServlet;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
            // start the project
            bootStrap.start();
        } catch (Exception e) {
            log.error("BootStrap main error:", e);
        }
    }

    /**
     * initialize a server.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void start() throws Exception {
        // load and parse related configuration, such as web.xml
        loadServlet();

        // create a threadPoolExecutor
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

        // create a serverSocket
        ServerSocket serverSocket =
                new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        log.info("server start on host: {}, port: {}", host, port);

        while (true) {
            // create client socket
            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket, servletMap);
            threadPoolExecutor.execute(requestProcessor);
        }
    }

    /**
     * create a threadPoolExecutor.
     *
     * @return a threadPoolExecutor
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

    /**
     * use dom4j to load and parse web.xml.
     */
    private void loadServlet() {
        // read the file which named "web.xml" in the resources directory
        log.info("load web.xml: {}", Objects.requireNonNull(this.getClass().getClassLoader().getResource("web.xml")).getPath());
        InputStream resourceAsStream =
                this.getClass().getClassLoader().getResourceAsStream("web.xml");
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();

            List<Node> selectNodes = rootElement.selectNodes("//servlet");
            selectNodes.forEach(
                    node -> {
                        // <servlet-name>wangguangwu</servlet-name>
                        Element servletNameElement = (Element) node.selectSingleNode("servlet-name");
                        String servletName = servletNameElement.getStringValue();
                        // <servlet-class>server.servlet.impl.WangServlet</servlet-class>
                        Element servletClassElement = (Element) node.selectSingleNode("servlet-class");
                        String servletClass = servletClassElement.getStringValue();

                        // find url-pattern based on the value of servlet-name
                        Element servletMapping =
                                (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                        // /wang
                        String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();
                        try {
                            // save the mapping relationship between url-pattern and the corresponding instance
                            log.info("servletClass: {}", servletClass);
                            servletMap.put(urlPattern, (HttpServlet) Class.forName(servletClass).newInstance());
                        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                            log.error("BootStrap loadServlet error: ", e);
                        }
                    }
            );
        } catch (DocumentException e) {
            log.error("BootStrap loadServlet error: ", e);
        }
    }

}
