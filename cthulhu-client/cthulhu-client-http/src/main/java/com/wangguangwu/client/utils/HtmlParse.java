package com.wangguangwu.client.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * some methods to operate html.
 *
 * @author wangguangwu
 * @date 2022/2/13
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

}
