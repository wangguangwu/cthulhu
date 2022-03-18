package com.wangguangwu.utils;


import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Map;

/**
 * @author yuanzhixiang
 */
public class BossUtil implements AutoCloseable{

    private static final ChromeDriver CHROME_DRIVER;

    static {
        Map<String, String> map = System.getenv();
        String userName = map.get("USER");
        String driverPath = "wangguangwu".equals(userName)
                ? "/Users/wangguangwu/workSpace/tomcat/driver/chromedriver" : "/Users/yuanzhixiang/Desktop/chromedriver";

        System.setProperty("webdriver.chrome.driver", driverPath);
        CHROME_DRIVER = new ChromeDriver();
    }

    public String getZpsToken(String url) {
        CHROME_DRIVER.get(url);
        for (Cookie cookie : CHROME_DRIVER.manage().getCookies()) {
            if ("__zp_stoken__".equals(cookie.getName())) {
                return "__zp_stoken__=" + cookie.getValue() + ";";
            }
        }
        throw new IllegalStateException("Zps token not found");
    }


    public static void main(String[] args) {
        BossUtil bossUtil = new BossUtil();
        System.out.println(bossUtil.getZpsToken("https://www.zhipin.com/c101210100/?query=java&page=2&ka=page-1"));
        System.out.println(bossUtil.getZpsToken("https://www.zhipin.com/c101210100/?query=java&page=2&ka=page-1"));
    }

    @Override
    public void close() {
        CHROME_DRIVER.quit();
    }
}
