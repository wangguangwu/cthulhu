package com.wangguangwu.client;

import com.wangguangwu.client.builder.Builder;
import com.wangguangwu.client.builder.RequestBuilder;
import com.wangguangwu.client.entity.ZhipinData;
import com.wangguangwu.client.startup.Bootstrap;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author wangguangwu
 */
public class ApiTest {

    @Test
    public void test() {
        Bootstrap bootstrap = new Bootstrap();
        List<ZhipinData> start = bootstrap.url("https://www.zhipin.com/job_detail/?query=java&city=101210100&industry=&position=").start();
        System.out.println();
    }

}
