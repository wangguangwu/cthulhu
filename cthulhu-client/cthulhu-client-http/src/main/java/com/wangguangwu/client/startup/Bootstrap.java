package com.wangguangwu.client.startup;

import com.wangguangwu.client.service.Crawler;
import com.wangguangwu.client.service.impl.CrawlerImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * visit the website corresponding to the url
 * parse response from the website.
 *
 * @author wangguangwu
 */
@Slf4j
@Getter
@Setter
public class Bootstrap {

    private int port = 443;

    private String url = "www.zhipin.com/robots.txt";

    public static void main(String[] args) {
        log.info("client is running...");
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.start();
        log.info("client closed");
    }

    private void start() {
        Crawler crawler = new CrawlerImpl();
        // get the robot's protocol of the specified website
        crawler.parseRobotsProtocol("www.zhipin.com");
//        crawler.crawler(url, port);
    }
}
