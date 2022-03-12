package com.wangguangwu.client.zpstoken;

import com.wangguangwu.client.utils.StringUtil;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * @author wangguangwu
 */
public class Main {

    public static void main(String[] args) throws IOException {

        String zpStoken = "f71cdCzQReTtlBWQBdwRiTzIQDVIiCD4ELWAcbRluY0xoUxAXIT1ifnMmTHp7IydKQV0HGHoKCS4wehlAdl8TLhZUUQ5lcxpQM0ExD1QNXyIEOn0mTAgIXRpIMRpJWQQ/A2Q1bGAACwV4IR0=";

        queryBossJobList(createNewZpStoken());
//        queryBossJobList(zpStoken);
    }

    private static String createNewZpStoken() throws IOException {
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .followRedirects(false)
                .followSslRedirects(false)
                .build();

        Seed seed = querySeed(client);

        String s = querySecurityJs(client, seed.getName());

        Request request = new Request.Builder()
                .url("https://www.zhipin.com/" + seed.getLocation())
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        }
        return "";
    }

    private static String querySecurityJs(OkHttpClient client, String name) throws IOException {
        Request request = new Request.Builder()
                .url("https://www.zhipin.com/web/common/security-js/" + name + ".js")
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();

            jsObjFunc(string, "EJ", "CdxfcyaJjIuvCmCWC0g4geovZXmTW0hMivzoRQtfSfU", 1647063575704L);
            return string;
        }
    }

    public static Object jsObjFunc(String jsStr, String function, Object... args) {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine scriptEngine = sem.getEngineByName("js");
        try {
            Object eval = scriptEngine.eval(jsStr);
            ScriptContext context = scriptEngine.getContext();
            Invocable inv2 = (Invocable) scriptEngine;
            return inv2.invokeFunction(function, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Seed querySeed(OkHttpClient client) throws IOException {
        Request request = new Request.Builder()
                .url("https://www.zhipin.com/job_detail/?query=java&city=101210100&industry=&position=")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() != 302) {
                throw new IllegalArgumentException("The response code is not 302");
            }

            String location = URLDecoder.decode(response.header("location"));

            if (location == null) {
                throw new IllegalArgumentException("The location can't null");
            }

            Map<String, String> headMap = StringUtil.parseQueryString(location);

            String seed = headMap.get("seed");
            String name = headMap.get("name");
            Long ts = Long.valueOf(headMap.get("ts"));

            return new Seed(location, seed, name, ts);
        }
    }

    private static void queryBossJobList(String zpStoken) throws IOException {
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .followRedirects(false)
                .followSslRedirects(false)
                .build();

        Request request = new Request.Builder()
                .url("https://www.zhipin.com/job_detail/?query=java&city=101210100&industry=&position=")
                .addHeader("cookie", "__zp_stoken__=" + zpStoken + ";")
                .build();

        final Call call = client.newCall(request);


        try (Response response = call.execute()) {
            System.out.println(response.body().string());
        }
    }
}
