package com.wangguangwu.client.http.network.crawler;

/**
 * @author wangguangwu
 * @version 1.0
 * @date 11:51 PM 2022/2/12
 * @description 爬虫接口
 */

public interface Crawler {

    /**
     * 访问 url 对应的网站
     *
     * @param url  url
     * @param port port
     */
    void crawler(String url, int port);

}
