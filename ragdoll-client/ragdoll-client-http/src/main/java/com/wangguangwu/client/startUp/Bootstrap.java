package com.wangguangwu.client.startUp;

import com.wangguangwu.client.service.Crawler;
import com.wangguangwu.client.service.impl.CrawlerImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/**
 * @author wangguangwu
 * @date 2022/2/12 8:10 PM
 * @description 访问百度官网并且获得响应
 */
@Slf4j
@Getter
@Setter
public class Bootstrap {

    private int port = 443;

    private String url = "www.baidu.com?client=1";

    public static void main(String[] args) {
        log.info("client is running...");
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.start();
        log.info("client closed");
    }

    private void start() {
        Crawler crawler = new CrawlerImpl();
        crawler.crawler(url, port);
    }
}
