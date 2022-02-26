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
     *
     * @param visitUrl visit url
     * @return map
     */
    Map<String, String> crawler(String visitUrl);


    /**
     * get the href from the response.
     *
     * @param url url
     */
    @SuppressWarnings("unused")
    void work(String url);

}
