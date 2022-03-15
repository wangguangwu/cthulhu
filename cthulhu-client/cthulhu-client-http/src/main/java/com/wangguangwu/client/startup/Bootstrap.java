package com.wangguangwu.client.startup;

import com.wangguangwu.client.entity.ZhipinData;
import com.wangguangwu.client.service.Work;
import com.wangguangwu.client.service.impl.WorkImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * visit the website corresponding to the url
 * parse response from the website.
 *
 * @author wangguangwu
 */
@Slf4j
public class Bootstrap {

    private String url;

    public Bootstrap url(String url) {
        this.url = url;
        return this;
    }

    /**
     * start cthulhu
     *
     * @return data
     */
    public List<ZhipinData> start() {
        log.info("Cthulhu client access website: {}", url);
        Work work = new WorkImpl(url);
        return work.work();
    }
}
