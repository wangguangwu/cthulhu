package com.wangguangwu.client.utils;

import com.wangguangwu.client.entity.SalaryData;
import com.wangguangwu.client.entity.Symbol;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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

    public static List<SalaryData> parseHtml(String html) {
        html = formatHtml(html);
        Document doc = Jsoup.parse(html);

        Elements elements = doc.select("[class=info-primary]");
        for (Element element : elements) {
            System.out.println();
        }

        Elements links = doc.select("a[href]");

        List<SalaryData> list = new ArrayList<>();
        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.text();

            if ((linkText.contains("java") || linkText.contains("Java"))) {
                if (linkText.contains(Symbol.SPACE)) {
                    SalaryData data = new SalaryData();
                    int index1 = linkText.indexOf(Symbol.SPACE);
                    int index2 = linkText.indexOf(Symbol.SPACE, index1 + 1);
                    data.setName(linkText.substring(0, index1));

                    if (index2 > 0) {
                        data.setSalary(linkText.substring(index1 + 1, index2));
                        data.setDescription(linkText.substring(index2 + 1));
                    } else {
                        data.setDescription(linkText.substring(index1 + 1));
                    }

                    list.add(data);
                }
            }

            if (linkText.contains("java") || linkText.contains("Java")) {
                System.out.println(linkHref + " : " + linkText);
            }
        }
        return list;
    }
}
