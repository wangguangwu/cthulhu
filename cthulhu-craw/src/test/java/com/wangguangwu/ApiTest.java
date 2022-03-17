package com.wangguangwu;

import com.wangguangwu.client.startup.Bootstrap;
import com.wangguangwu.utils.BossUtil;
import org.junit.jupiter.api.Test;


/**
 * @author wangguangwu
 */
public class ApiTest {

    @Test
    public void test() {
        Bootstrap bootstrap = new Bootstrap();
        BossUtil bossUtil = new BossUtil();
        String url = "https://www.zhipin.com/c101210100/?query=java&page=2&ka=page-1";
        String responseBody = bootstrap
                .url(url)
                .cookies(bossUtil.getZpsToken(url)).start();
        System.out.println(responseBody);
    }

}
