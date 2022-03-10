package com.wangguangwu.client.startup;

import com.wangguangwu.client.entity.ZhipinData;

import java.util.List;

/**
 * @author wangguangwu
 */
public class Test {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.setUrl("https://www.zhipin.com/job_detail/?query=java&city=101210100&industry=&position=");
        List<ZhipinData> data = bootstrap.start();
        data.forEach(System.out::println);
    }

}
