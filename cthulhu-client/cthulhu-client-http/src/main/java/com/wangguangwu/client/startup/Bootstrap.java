package com.wangguangwu.client.startup;

import com.wangguangwu.client.service.Work;
import com.wangguangwu.client.service.impl.WorkImpl;
import lombok.extern.slf4j.Slf4j;


/**
 * visit the website corresponding to the url
 * parse response from the website.
 *
 * @author wangguangwu
 */
@Slf4j
public class Bootstrap {

    private String url;

    private String cookies;

    public Bootstrap url(String url) {
        this.url = url;
        return this;
    }

    public Bootstrap cookies(String cookies) {
        this.cookies = cookies;
        return this;
    }

    /**
     * start cthulhu
     *
     * @return responseBody
     */
    public String start() {
        log.info("Cthulhu client access website: {}, cookies: {}", url, cookies);
        Work work = new WorkImpl(url, cookies);
        return work.work();
    }
}
