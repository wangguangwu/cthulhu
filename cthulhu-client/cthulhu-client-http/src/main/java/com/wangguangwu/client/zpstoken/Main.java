package com.wangguangwu.client.zpstoken;

import okhttp3.*;

import java.io.IOException;

/**
 * @author wangguangwu
 */
public class Main {

    public static void main(String[] args) throws IOException {

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .followRedirects(false)
                .followSslRedirects(false)
                .build();
        Request request = new Request.Builder()
                .url("https://www.zhipin.com/job_detail/?query=java&city=101210100&industry=&position=")
                .build();

        final Call call = client.newCall(request);

        try (Response response = call.execute()) {
            System.out.println(response.body().string());
        }
    }
}
