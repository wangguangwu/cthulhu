package com.wangguangwu.client.utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Map;

/**
 * @author yuanzhixiang
 */
public class BossUtil {

    private static final ChromeDriver CHROME_DRIVER;

    static {
        Map<String, String> map = System.getenv();
        String userName = map.get("USER");
        String driverPath = "wangguangwu".equals(userName)
                ? "/Users/wangguangwu/workSpace/tomcat/driver/chromedriver" : "/Users/yuanzhixiang/Desktop/chromedriver";

        System.setProperty("webdriver.chrome.driver", driverPath);
        CHROME_DRIVER = new ChromeDriver();
    }

    public static String getZpsToken() {
        CHROME_DRIVER.get("https://www.zhipin.com/c101210100-p100101/");
        for (Cookie cookie : CHROME_DRIVER.manage().getCookies()) {
            if ("__zp_stoken__".equals(cookie.getName())) {
                return "__zp_stoken__=" + cookie.getValue();
            }
        }
        throw new IllegalStateException("Zps token not found");
    }


    public static void main(String[] args) {
        System.out.println(getZpsToken());
        System.out.println(getZpsToken());
    }

}
