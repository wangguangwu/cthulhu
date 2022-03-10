package com.wangguangwu.client.service;

import com.wangguangwu.client.entity.ZhipinData;

import java.util.List;

/**
 * @author wangguangwu
 */
public interface Work {

    /**
     * access the URL and the URLs two layers below it.
     *
     * @return list of salaryData
     */
    List<ZhipinData> work();

}
