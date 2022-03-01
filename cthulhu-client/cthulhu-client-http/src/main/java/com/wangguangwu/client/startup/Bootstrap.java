package com.wangguangwu.client.startup;

import com.wangguangwu.client.service.Work;
import com.wangguangwu.client.service.impl.WorkImpl;
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

//    private String url = "https://www.zhipin.com/hangzhou/";
    private String url = "http://search.dangdang.com/advsearch";

    public static void main(String[] args) {
        log.info("client is running...");
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.start();
        log.info("client closed");
    }

    private void start() {
        Work work = new WorkImpl();
        work.work(url);
    }
}
