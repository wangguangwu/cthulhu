package com.wangguangwu.service.impl;

import com.alibaba.fastjson.JSON;
import com.wangguangwu.client.entity.DataParse;
import com.wangguangwu.client.entity.Symbol;

import com.wangguangwu.client.utils.HtmlParse;
import com.wangguangwu.entity.Company;
import com.wangguangwu.entity.Job;
import com.wangguangwu.entity.JobExample;
import com.wangguangwu.entity.ZhipinData;
import com.wangguangwu.mapper.CompanyMapper;
import com.wangguangwu.mapper.JobMapper;
import com.wangguangwu.service.CthulhuService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.wangguangwu.client.utils.StringUtil.findFirstIndexNumberOfStr;

/**
 * @author wangguangwu
 */
@Slf4j
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
            companyMapper.insertSelective(zhipinData.getCompany());
            jobMapper.insertSelective(zhipinData.getJob());
        });
    }

    @Override
    public String queryData() {
        JobExample jobExample = new JobExample();
        jobExample.createCriteria().andIdIsNotNull();
        List<Job> jobs = jobMapper.selectByExample(jobExample);
        return JSON.toJSONString(jobs);
    }

    /**
     * parse zhipin data.
     *
     * @param html data from zhipin
     * @return zhipinData
     */
    @Override
    public List<ZhipinData> analysisData(String html) {
        html = HtmlParse.formatHtml(html);
        Document doc = Jsoup.parse(html);

        Elements rows = new Elements();

        if (doc.select(DataParse.JOB_LIST).size() > 0) {
            rows = doc.select("div[class=job-list]").get(0).select("ul");
        }

        List<ZhipinData> list = new ArrayList<>();

        if (rows.size() == 0) {
            log.info("no results");
        } else {
            for (int i = 0; i < rows.get(0).select(DataParse.LI).size(); i++) {
                ZhipinData data = new ZhipinData();
                Company company = new Company();
                Job job = new Job();

                Elements jobPrimary = rows.get(0).select("li").get(i).select("[class=job-primary]");
                // basic part
                Elements infoPrimary = jobPrimary.select("[class=info-primary]");
                Elements infoAppend = jobPrimary.select("[class=info-append clearfix]");

                // primary-box
                Elements primaryBox = infoPrimary.select("[class=primary-wrapper]")
                        .select("[class=primary-box]");
                // company-text
                Elements companyText = infoPrimary.select("[class=info-company]")
                        .select("[class=company-text]");

                String companyName = companyText.select("h3").text();
                company.setCompanyName(companyName);
                job.setJobCompany(companyName);

                final Elements p = companyText.select("p");
                final String companyInfo = p.text();
                // companyIndustry
                final String companyIndustry = p.select("a").text();

                company.setCompanyIndustry(companyIndustry);
                String companyStatus = companyInfo.substring(companyIndustry.length());

                int statusIndex = findFirstIndexNumberOfStr(companyStatus);

                if (statusIndex > 0) {
                    company.setCompanySize(companyStatus.substring(statusIndex));
                    companyStatus = companyStatus.substring(0, statusIndex);
                }
                company.setCompanyStatus(companyStatus);

                // jobTitle
                String jobTitle = primaryBox.select("[class=job-title]").text();
                int index = jobTitle.lastIndexOf(Symbol.SPACE);
                if (index != -1) {
                    job.setJobName(jobTitle.substring(0, index));
                    company.setCompanyAddress(jobTitle.substring(index + 1));
                }

                // job-limit clearfix
                String jobLimitClearfix = primaryBox.select("[class=job-limit clearfix]").text();
                String[] strings = jobLimitClearfix.split(Symbol.SPACE);
                job.setJobSalary(strings[0]);
                job.setJobEducationRequire(strings[1]);

                // tags
                job.setJobSkillRequire(infoAppend.select("[class=tags]").text());
                // infoDesc
                job.setJobInfoDesc(infoAppend.select("[class=info-desc]").text());

                data.setCompany(company);
                data.setJob(job);

                list.add(data);
            }
        }
        return list;
    }

}
