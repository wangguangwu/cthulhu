package com.wangguangwu.client.utils;

import java.util.Map;

/**
 * @author wangguangwu
 */
public class TokenUtil {

    /**
     * get zp_stoken from www.zhipin.com
     *
     * @param url url
     * @return zp_stoken
     */
    public static String getZpToken(String url) {
        Map<String, String> map = StringUtil.parseQueryString(url);
        String seed = map.get("seed");
        String name = map.get("name");
        String ts = map.get("ts");
        return "__zp_stoken__=8c31dKUgZRUI%2FLWwUVXklWzNtVTA1DUAdcQMgDSRTLkstNF0tbERLRH8jJ18ufCRfHnZfOl9DXUJEJjhnMEIieWpAbk5Gdmg1IXEFKAEfSkQzEUIKLTRRKAJZFU9ZKglNXE9tTi08UD94PDk%3D;";
    }

}
