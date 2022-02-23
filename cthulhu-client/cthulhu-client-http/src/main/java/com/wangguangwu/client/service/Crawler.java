package com.wangguangwu.client.service;

import java.util.Map;

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
     *  @param url  url
     * @param port port
     * @return map
     */
    Map<String, String> crawler(String url, int port);

    /**
     * get the robot's protocol of the specified website.
     *
     * @param host website domain
     */
    void parseRobotsProtocol(String host);

}
