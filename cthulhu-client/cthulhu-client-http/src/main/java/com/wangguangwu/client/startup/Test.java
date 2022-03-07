package com.wangguangwu.client.startup;

import com.wangguangwu.client.entity.SalaryData;

import java.util.List;

/**
 * @author wangguangwu
 */
public class Test {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.setUrl("https://www.zhipin.com/hangzhou/");
        List<SalaryData> data = bootstrap.start();
        data.forEach(System.out::println);
    }

}
