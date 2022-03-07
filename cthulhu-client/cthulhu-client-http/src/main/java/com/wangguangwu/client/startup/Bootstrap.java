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

    public List<SalaryData> start() {
        log.info("Cthulhu client access website: {}", url);
        WorkImpl work = new WorkImpl(url);
        return work.work();
    }
}
