package com.wangguangwu.client.utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author yuanzhixiang
 */
public class BossUtil {

    private static final ChromeDriver CHROME_DRIVER;

    static {
        // todo 这个驱动去 https://registry.npmmirror.com/binary.html?path=chromedriver/ 下载
        //   下载好了把驱动路径配置进来就行了
        String driverPath = "/Users/yuanzhixiang/Desktop/chromedriver";
        System.setProperty("webdriver.chrome.driver", driverPath);
        CHROME_DRIVER = new ChromeDriver();
    }

    public static String getZpsToken() {
        CHROME_DRIVER.get("https://www.zhipin.com/c101210100-p100101/");
        for (Cookie cookie : CHROME_DRIVER.manage().getCookies()) {
            if ("__zp_stoken__".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        throw new IllegalStateException("Zps token not found");
    }

    public static void main(String[] args) {
        System.out.println(getZpsToken());
    }

}
