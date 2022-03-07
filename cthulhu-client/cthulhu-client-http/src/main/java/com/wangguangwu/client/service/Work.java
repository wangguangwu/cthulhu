package com.wangguangwu.client.service;

import com.wangguangwu.client.entity.SalaryData;

import java.util.List;

/**
 * @author wangguangwu
 */
public interface Work {

    /**
     * access the URL and the URLs two layers below it.
     *
     * @param url fullUrl, such as https://www.baidu.com/index.html
     * @return list of salaryData
     */
    List<SalaryData> work(String url);

}
