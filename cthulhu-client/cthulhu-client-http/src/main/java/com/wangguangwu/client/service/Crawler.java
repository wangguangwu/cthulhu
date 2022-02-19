package com.wangguangwu.client.service;

/**
 * visit the website corresponding to the url
 * parse response from the website.
 *
 * @author wangguangwu
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
