package com.wangguangwu.server.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangguangwu
 */
public class ParseRequestUtil {

    public static Map<String, String> parseQueryString(String url) {
        Map<String, String> params = new ConcurrentHashMap<>();
        url = url.substring(url.indexOf("?") + 1);

        String[] parameters = url.contains("&")
                ? url.split("&") : new String[]{url};
        for (String parameter : parameters) {
            if (parameter.contains("=")) {
                String key = parameter.substring(0, parameter.indexOf("="));
                String value = parameter.substring(parameter.indexOf("=") + 1);
                params.put(key, value);
            }
        }
        return params;
    }

}
