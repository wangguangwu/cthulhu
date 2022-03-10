package com.wangguangwu.client.utils;

import com.wangguangwu.client.entity.Symbol;
import com.wangguangwu.client.entity.ZhipinData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

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
                data.setCompanyInfo(companyText.select("p").text());

                // jobTitle
                String jobTitle = primaryBox.select("[class=job-title]").text();
                int index = jobTitle.lastIndexOf(Symbol.SPACE);
                if (index != -1) {
                    data.setJob(jobTitle.substring(0, index));
                    data.setAddress(jobTitle.substring(index + 1));
                }

                // job-limit clearfix
                String jobLimitClearfix = primaryBox.select("[class=job-limit clearfix]").text();
                String[] strings = jobLimitClearfix.split(Symbol.SPACE);
                data.setSalary(strings[0]);
                data.setAcademicRequirements(strings[1]);

                // info-detail
                data.setInfoDetail(primaryBox.select("[class=info-detail]").text());

                // tags
                data.setSkillRequirements(infoAppend.select("[class=tags]").text());
                // infoDesc
                data.setInfoDesc(infoAppend.select("[class=info-desc]").text());

                list.add(data);
                System.out.println(data);

            }
        }

        return list;
    }
}
