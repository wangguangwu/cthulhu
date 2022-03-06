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
    private String url = "https://www.zhipin.com/job_detail/?query=java&city=101210100&industry=&position=";

    public static void main(String[] args) {
        log.info("client is running...");
        new Bootstrap().start();
        log.info("client closed");
    }

    private void start() {
        Work work = new WorkImpl();
        work.work(url);
    }
}
