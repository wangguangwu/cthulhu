package com.wangguangwu.client.service;

/**
 * visit the website corresponding to the url
 * parse response from the website.
 *
 * @author wangguangwu
 * @date 2022/2/12
 */

public interface Crawler {

    /**
     * visit the website corresponding to the url
     * parse response from the website.
     *
     * @param url  url
     * @param port port
     */
    void crawler(String url, int port);

}
