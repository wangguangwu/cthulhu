package com.wangguangwu.client.utils;

import com.wangguangwu.client.entity.Symbol;
import com.wangguangwu.client.entity.ZhipinData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static com.wangguangwu.client.utils.StringUtil.findFirstIndexNumberOfStr;

/**
 * some methods to operate html.
 *
 * @author wangguangwu
 */
public class HtmlParse {

    /**
     * format html.
     *
     * @param html unformatted html
     * @return formatted html
     */
    public static String formatHtml(String html) {
        Document document = Jsoup.parseBodyFragment(html);
        return document.html();
    }

    public static List<ZhipinData> analysisData(String html) {
        html = formatHtml(html);
        Document doc = Jsoup.parse(html);

        Elements rows = doc.select("div[class=job-list]").get(0).select("ul");

        List<ZhipinData> list = new ArrayList<>();

        if (rows.size() == 0) {
            System.out.println("没有结果");
        } else {
            for (int i = 0; i < rows.get(0).select("li").size(); i++) {
                ZhipinData data = new ZhipinData();

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

                data.setCompanyName(companyText.select("h3").text());

                final Elements p = companyText.select("p");
                final String companyInfo = p.text();
                // companyIndustry
                final String companyIndustry = p.select("a").text();

                data.setCompanyIndustry(companyIndustry);
                String companyStatus = companyInfo.substring(companyIndustry.length());

                int statusIndex = findFirstIndexNumberOfStr(companyStatus);

                if (statusIndex > 0) {
                    data.setCompanySize(companyStatus.substring(statusIndex));
                    companyStatus = companyStatus.substring(0, statusIndex);
                }
                data.setCompanyStatus(companyStatus);

                // jobTitle
                String jobTitle = primaryBox.select("[class=job-title]").text();
                int index = jobTitle.lastIndexOf(Symbol.SPACE);
                if (index != -1) {
                    data.setJobName(jobTitle.substring(0, index));
                    data.setCompanyAddress(jobTitle.substring(index + 1));
                }

                // job-limit clearfix
                String jobLimitClearfix = primaryBox.select("[class=job-limit clearfix]").text();
                String[] strings = jobLimitClearfix.split(Symbol.SPACE);
                data.setJobSalary(strings[0]);
                data.setJobEducationRequire(strings[1]);

                // tags
                data.setJobSkillRequire(infoAppend.select("[class=tags]").text());
                // infoDesc
                data.setJobInfoDesc(infoAppend.select("[class=info-desc]").text());

                list.add(data);
                System.out.println(data);

            }
        }

        return list;
    }
}
