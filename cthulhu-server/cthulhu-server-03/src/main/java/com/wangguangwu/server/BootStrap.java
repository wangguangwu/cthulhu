package com.wangguangwu.server;

import com.wangguangwu.server.servlet.impl.HttpServlet;
import com.wangguangwu.server.thread.RequestProcessor;
import lombok.Getter;
import lombok.Setter;
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
import java.util.concurrent.*;

/**
 * @author wangguangwu
 * @date 2022/2/5 11:58 PM
 * @description tomcat 启动类
 */
@Getter
@Setter
public class BootStrap {

    private int port = 8080;

    /**
     * tomcat 的程序启动入口
     *
     * @param args args
     */
    public static void main(String[] args) {
        BootStrap bootStrap = new BootStrap();
        try {
            bootStrap.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * tomcat 启动需要初始化展开的一些操作
     * 完成 tomcat 3.0 版本
     * 需求：可以请求动态资源（Servlet）
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void start() throws Exception {
        // 加载解析相关的配置，web.xml
        loadServlet();

        // 定义一个线程池
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

        // 创建一个服务端 socket
        ServerSocket serverSocket =
                new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        System.out.println("======>>tomcat start on port " + port);

        // 多线程改造 使用线程池
        while (true) {
            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket, servletMap);
            threadPoolExecutor.execute(requestProcessor);
        }
    }

    /**
     * 生成一个线程池
     *
     * @return 线程池
     */
    private ThreadPoolExecutor getThreadPoolExecutor() {
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
     * 创建一个 mpa，保存 url 和 servlet 的映射关系
     */
    private Map<String, HttpServlet> servletMap = new HashMap<>();

    /**
     * 使用 dom4j 加载解析 web.xml，初始化 Servlet
     */
    private void loadServlet() {
        // 读取文件
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

                        // 根据 servlet-name 的值找到 url-pattern
                        Element servletMapping =
                                (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                        // /wang
                        String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();
                        try {
                            // 保存映射关系
                            servletMap.put(urlPattern, (HttpServlet) Class.forName(servletClass).newInstance());
                        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
            );
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
