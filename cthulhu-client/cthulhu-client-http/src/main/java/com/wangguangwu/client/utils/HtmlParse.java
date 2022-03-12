package com.wangguangwu.client.utils;

import com.wangguangwu.client.entity.DataParse;
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

}
