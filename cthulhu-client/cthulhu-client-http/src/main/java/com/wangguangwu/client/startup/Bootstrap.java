package com.wangguangwu.client.startup;

import com.wangguangwu.client.entity.SalaryData;
import com.wangguangwu.client.service.Work;
import com.wangguangwu.client.service.impl.WorkImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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

    private String url;
//    private String url = "https://www.zhipin.com/job_detail/?query=java&city=101210100&industry=&position=";

    public List<SalaryData> start() {
        log.info("client started...");
        Work work = new WorkImpl();
        List<SalaryData> data = work.work(url);
        log.info("client closed");
        return data;
    }
}
