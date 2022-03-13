package com.wangguangwu.service.impl;

import com.alibaba.fastjson.JSON;
import com.wangguangwu.client.entity.ZhipinData;
import com.wangguangwu.entity.Company;
import com.wangguangwu.entity.Job;
import com.wangguangwu.entity.JobExample;
import com.wangguangwu.mapper.CompanyMapper;
import com.wangguangwu.mapper.JobMapper;
import com.wangguangwu.service.CthulhuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangguangwu
 */
@Service
public class CthulhuServiceImpl implements CthulhuService {

    private final CompanyMapper companyMapper;

    private final JobMapper jobMapper;

    @Autowired
    private CthulhuServiceImpl(CompanyMapper companyMapper, JobMapper jobMapper) {
        this.companyMapper = companyMapper;
        this.jobMapper = jobMapper;
    }

    @Override
    public void saveData(List<ZhipinData> data) {
        data.forEach(zhipinData -> {
            Company company = new Company();
            company.setCompanyName(zhipinData.getCompanyName());
            company.setCompanyAddress(zhipinData.getCompanyAddress());
            company.setCompanySize(zhipinData.getCompanySize());
            company.setCompanyIndustry(zhipinData.getCompanyIndustry());
            company.setCompanyStatus(zhipinData.getCompanyStatus());
            companyMapper.insert(company);
            Job job = new Job();
            job.setJobName(zhipinData.getJobName());
            job.setJobSalary(zhipinData.getJobSalary());
            job.setJobInfoDesc(zhipinData.getJobInfoDesc());
            job.setJobEducationRequire(zhipinData.getJobEducationRequire());
            job.setJobSkillRequire(zhipinData.getJobSkillRequire());
            job.setJobCompany(zhipinData.getCompanyName());
            jobMapper.insert(job);
            System.out.println();
        });
    }

    @Override
    public String queryData() {
        JobExample jobExample = new JobExample();
        jobExample.createCriteria().andIdIsNotNull();
        List<Job> jobs = jobMapper.selectByExample(jobExample);
        return JSON.toJSONString(jobs);
    }

}
