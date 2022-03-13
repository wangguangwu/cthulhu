package com.wangguangwu.service;

import com.wangguangwu.client.entity.ZhipinData;

import java.util.List;

/**
 * @author wangguangwu
 */
public interface CthulhuService {

    /**
     * save data.
     *
     * @param data data
     */
    void saveData(List<ZhipinData> data);

    /**
     * query data from database
     * @return json data
     */
    String queryData();
}
