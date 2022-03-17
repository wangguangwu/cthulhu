package com.wangguangwu.entity;


import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

/**
 * Parse responseBody and get the salary message.
 *
 * @author wangguangwu
 */
@Getter
@Setter
public class ZhipinData {

    private Job job;

    private Company company;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
