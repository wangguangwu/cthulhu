package com.wangguangwu.client.service;

/**
 * @author wangguangwu
 */
public interface Work {

    /**
     * access the URL and the URLs two layers below it.
     * @param url fullUrl, such as https://www.baidu.com/index.html
     */
    void work(String url);

}
