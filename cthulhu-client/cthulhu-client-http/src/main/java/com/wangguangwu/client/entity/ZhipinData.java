package com.wangguangwu.client.entity;

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

    /**
     * job
     */
    private String jobTitle;

    /**
     * address
     */
    private String address;

    /**
     * salary
     */
    private String salary;

    /**
     * academic requirements
     */
    private String academicRequirements;

    private String companyName;

    private String companyInfo;

    private String infoDetail;

    /**
     * skill requirements, such as SQL redis and  so on
     */
    private String skillRequirements;

    private String infoDesc;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
