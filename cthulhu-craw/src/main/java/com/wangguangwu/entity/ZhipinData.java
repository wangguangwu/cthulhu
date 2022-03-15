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
//
//    private String jobName;
//
//    private String jobSalary;
//
//    private String jobEducationRequire;
//
//    private String jobSkillRequire;
//
//    private String jobInfoDesc;


    //======================================

//    private String companyName;
//
//    private String companyAddress;
//
//    private String companyIndustry;
//
//    private String companyStatus;
//
//    private String companySize;

    private Company company;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
