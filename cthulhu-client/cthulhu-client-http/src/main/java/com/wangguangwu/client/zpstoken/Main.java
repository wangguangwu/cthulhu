//package com.wangguangwu.client.zpstoken;
//
//import com.wangguangwu.client.utils.StringUtil;
//import okhttp3.*;
//
//import javax.script.Invocable;
//import javax.script.ScriptEngine;
//import javax.script.ScriptEngineManager;
//import java.io.IOException;
//import java.net.URLDecoder;
//import java.util.Map;
//
///**
// * @author wangguangwu
// */
//public class Main {
//
//    public static void main(String[] args) throws IOException {
//
//        String zpStoken = "f71cdCzQReTtlBWQBdwRiTzIQDVIiCD4ELWAcbRluY0xoUxAXIT1ifnMmTHp7IydKQV0HGHoKCS4wehlAdl8TLhZUUQ5lcxpQM0ExD1QNXyIEOn0mTAgIXRpIMRpJWQQ/A2Q1bGAACwV4IR0=";
//
//        queryBossJobList(createNewZpStoken());
////        queryBossJobList(zpStoken);
//    }
//
//    private static String createNewZpStoken() throws IOException {
//        OkHttpClient client = new OkHttpClient()
//                .newBuilder()
//                .followRedirects(false)
//                .followSslRedirects(false)
//                .build();
//
//        Seed seed = querySeed(client);
//
//        String s = querySecurityJs(client, seed.getName());
//
//        Request request = new Request.Builder()
//                .url("https://www.zhipin.com/" + seed.getLocation())
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            System.out.println(response.body().string());
//        }
//        return "";
//    }
//
//    private static String querySecurityJs(OkHttpClient client, String name) throws IOException {
//        Request request = new Request.Builder()
//                .url("https://www.zhipin.com/web/common/security-js/" + name + ".js")
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            String string = response.body().string();
//
//            jsObjFunc(string, "EJ", "CdxfcyaJjIuvCmCWC0g4geovZXmTW0hMivzoRQtfSfU", 1647063575704L);
//            return string;
//        }
//    }
//
//    public static Object jsObjFunc(String jsStr, String function, String seed, Long ts) {
//        ScriptEngineManager sem = new ScriptEngineManager();
//        ScriptEngine scriptEngine = sem.getEngineByName("js");
//        try {
//            String core = "" +
//                    "   window = {\n" +
//                    "    document: {\n" +
//                    "        cookie: \"\",\n" +
//                    "        createElement: function(tag) {\n" +
//                    "            if (tag == \"canvas\") {\n" +
//                    "                return canvas\n" +
//                    "            } else if (tag == \"caption\") {\n" +
//                    "                return {\n" +
//                    "                    tagName: \"CAPTION\"\n" +
//                    "                }\n" +
//                    "            }\n" +
//                    "            \n" +
//                    "        },\n" +
//                    "        getElementById: function() {\n" +
//                    "            return false\n" +
//                    "        },\n" +
//                    "        title: \"\"\n" +
//                    "    },\n" +
//                    "    moveBy: function () {},\n" +
//                    "    moveTo: function () {},\n" +
//                    "    open: function(){},\n" +
//                    "    dispatchEvent: function(){return true},\n" +
//                    "    screen: {\n" +
//                    "        availHeight: 824,\n" +
//                    "        availWidth: 1536\n" +
//                    "    },\n" +
//                    "    navigator: {\n" +
//                    "        cookieEnabled: true,\n" +
//                    "        language: \"zh-CN\",\n" +
//                    "        userAgent: \"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36\",\n" +
//                    "        appVersion: \"5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36\"\n" +
//                    "    },\n" +
//                    "    decodeURI: global.decodeURI,\n" +
//                    "    location: {\n" +
//                    "        hostname: \"www.zhipin.com\",\n" +
//                    "        href: \"https://www.zhipin.com/\"\n" +
//                    "    },\n" +
//                    "    OfflineAudioContext: function () {\n" +
//                    "        this.createOscillator = function() {\n" +
//                    "            return {\n" +
//                    "                frequency: {\n" +
//                    "                    setValueAtTime: function() {}\n" +
//                    "                },\n" +
//                    "                connect: function (){},\n" +
//                    "                start: function (){},\n" +
//                    "            }\n" +
//                    "        },\n" +
//                    "        this.createDynamicsCompressor = function() {\n" +
//                    "            return {\n" +
//                    "                threshold: {\n" +
//                    "                    setValueAtTime: function () {},\n" +
//                    "                },\n" +
//                    "                setValueAtTime: function () {},\n" +
//                    "                knee: {\n" +
//                    "                    setValueAtTime: function () {},\n" +
//                    "                },\n" +
//                    "                ratio: {\n" +
//                    "                    setValueAtTime: function () {},\n" +
//                    "                },\n" +
//                    "                reduction: {\n" +
//                    "                    setValueAtTime: function () {},\n" +
//                    "                },\n" +
//                    "                attack: {\n" +
//                    "                    setValueAtTime: function () {},\n" +
//                    "                },\n" +
//                    "                release: {\n" +
//                    "                    setValueAtTime: function () {},\n" +
//                    "                },\n" +
//                    "                connect: function (){},\n" +
//                    "            }\n" +
//                    "        },\n" +
//                    "        this.startRendering = function (){}\n" +
//                    "    },\n" +
//                    "    eval: global.eval,\n" +
//                    "    history: {length: 1},\n" +
//                    "    outerHeight: 824,\n" +
//                    "    innerHeight: 150,\n" +
//                    "    outerWidth: 1536,\n" +
//                    "    innerWidth: 0,\n" +
//                    "    Math: global.Math,\n" +
//                    "    Date: global.Date,\n" +
//                    "}\n" +
//                    "window.open.toString = function (){return \"function open() { [native code] }\"}\n" +
//                    "document = window.document\n" +
//                    "navigator = window.navigator\n" +
//                    "screen = window.screen\n" +
//                    "canvas = {\n" +
//                    "    getContext: function getContext() {\n" +
//                    "        return CanvasRenderingContext2D\n" +
//                    "    },\n" +
//                    "    toDataURL: function toDataURL() {\n" +
//                    "        // 实际为canvas画布填充了“\"k54kk5cA4*\"”字样后，转成的图片链接\n" +
//                    "        return \"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAACWCAYAAABkW7XSAAANT0lEQVR4Xu2be3BU5R2Gf2cDBAomRC4Bwi1WEEMJGIjAgBUqyowXQKp08IJFIUvGVqkzlcFiWy+tF1pbtSNsUnHEqqjcxgEpgqKC0ghYJQJauTjKRQyRBAwEcfd0zpJdNzcU2FmSlyf/hXP2nO993uWZ7/vOiWP8QAACEGgkBJxGMk6GGUcCbp65cbxco7mUU2B83xtNW3UPlAIbeYEnM3yEdTLU+ExDIICwGkILCR4DwkowcG4XNwIIK24oG8+FEFbj6YqRVieAsM7AbwTCOgNLF4mMsESKPJEYCOtEaHFuQyKAsBpSGwkaC8JKEGhuE3cCCCvuSBv+BRFWw++IEdZNAGGdgd8MhHUGli4SGWGJFHkiMRDWidDi3IZEAGE1pDYSNBaElSDQ3CbuBBBW3JE2/AsirIbfESNkD4vvQBUBhMVXobESYIbVWJs7hXEjrFOAx0dPKwGEdVrxn56bI6zTw527njqB74Q1qTDdfKE3zHWmWmHe8lqX9s/ua66z0szaRo+5zmwrzMuvdW5e4DZznZFW2XycPTOhwmr+HvuB4x3zzptcMMscd0rMR/aZ446wwJQPflD873Jtj46nvg/WNRYvd8g3zSqbTw5nEfhBWAIlnqERfriwvP/M3k+B/7HjsoqIzXXePWVhebJx3DnmOjfbPyfvPamOIhJy3HPqlbF34Zrj9v6teeWLZlYevq/jXmBms743/0kNMrEfQliJ5c3d4keglrB6nFv00M+Gz7ls566s95a+cvs0c5354VmUN9MxW+zPzzvsBu12x7FmkWG4jm0u6G/T7Ma5La15ZWHVv6fePPG21U2SK7u/MO/eT8vLOlwU+f3Zfz3YrKIi7arwTMl1LvZmY3Ue8y5UNbvJmzrh945rWdF7uvaNk2SPBmYVjI+ZgX1kId+wqNyOjceTzmPt2306YdCgly7skPHJa8HmdteTve2rKMZ6xh0+79HADWZ29/Dhc6b16FHUN9jC7qn22fh1kbArIayEoeZGcSZQp7AGD54/7p13rh26devAa8PLw8h/aCeUk93ntfO8MWzceGnt5WBkFmZm3bu9P37EiIK9vibBL2KFtWfvuTe++caEXYe+6nB17HLRE1atY5MLRpov9MeUs/YN6tv3Ve+e+8sPtBseXQ5+39Lz2KzpCQv5xl511V/v/boidVxm9/dXNW165LlArs2PsjzOuL/9tlnPMaMfPGvX7vOHdOv2wdLmzQ61NMfWBwbYI3HuImGXQ1gJQ82N4kyglrC6dflwTq9eq2eUHUhfUHTL8pv979lPj36TfOfHHw8a/Pbb192T5/dnVFakFM995i/jzeyz6B5WzPItNW3PxP79l/gzM/+7LKnJUV9EWMOGPeU4TmjwvtLOg4t/veCjvA320P7STiPXrRt9uHv39/fXdWztf65JGzBg6e3t228bvmjx9NUlX3b39rRGWDDpi9g9N+9a0RmYawd9ZjNmFRT81uN1y9S839nBpjPXrh3XLzPzvf0ZXbZsCM8IvZ/jjDt2JuZfb3e4rg0Jz+py7K0495DQyyGshOLmZnEkUEtYKSklvQZeuPBIs2aVMzp33fxuePln9rw3I7llk52ddNj+4Ji1qzzSMqN0X5ecViml8+cNL5kYWTJ6M7JL5l246Ouv087rnfXm3OiSsDz9gew+Ky2j85bNnXp+eFFSZVgYqWGZ1XMsIgz/OrvGdexKx6xNSUm3PqGQL1h+oP2fVq2aeKc59gv/5CmXuI71jJ6/3u44cqRFvxdeuu+8w4db5fn9U1ItZBOXLJ36eWpKyRVDhszb2sQJTp+Va9uPN+7Y67mOpTuOLfWu43PtvvBn+YEABBJKoJawMjK2vDR40ILLd+zISc7JWbLZ5wsWRZY/+evsnJDZ/eEl0ezCZ1u0Kn957Jj7P6s8kuIumD+j1Jtteefs3vvjZ9asue6b0aNmLo8R1k3X/Pz+JaWlnSalpOxb3CF9W3J4FlQYuNLM8us8ViUFb3Zjrg2InN+3z4or+vb7d9Lrqyb2bNt2V/7A3IXXm9nCOpZ5j3o0Bw5cGIZaVDTWkpMrbPSoh19JS/vitUCgYJOZjalv3LX2uhJaDTeDAARqEqhzD+uioc+O+rIk89IWLQ5sO7vt7tsCswpamOP+vdqGdtX+0JgxD88MBX0z160ffe6ePT3Ccigp6Wbbt/e3nAuWfpmdvWL5osXTN0c23SsOtb6hoqJ1tzZtPnv66aGHfhV55cHbw6p2LDB7WnjD3HGXV3syd+w1hy03TfhNz63bcsd/ezT5gX79Xs0xn82OLtUim+2Ouzx/sn9JyLG7zWdPBf42d4N3zaFDn2+d9ZNVxVWb/xMjUqs57qTWh6c29g12vvIQUCJQ71PC3bt7Hfx8Z9b1vXu/vmxj8SV3Fxdftij6WL8eIVjQ2rs+yy0IBHbFPvmL3XRPanL0ghUr/F2z+6xo27HjJ9MDhYFOkXPrPGaWHxWltwlfJU7/lMnX79x5/uWbNg1rOWJEwedJTYP/iBHrreYLPhFZLprZ8EhpFRVp7crK0nunp29b2yTpaIE3KwsvOesYNzMspa86WRQI1Cssb4m1aNFdV+fmLh6Wnr798TlzHn+qZ6+3N3TpvDlj3fpRdqC8ffgpobcpH9nXCfrM7218l5V1yKw83Kpd+/QdG3y+YLC0tEvHVat+eTCyRFy27Nbn2qTteSUr661333zrhjW7dmb1j7zWEHsstdW+Sc+9eO+47OyVD5SXpdvG4hHRl0arNtn3Llp4V/8+2Suv2/FpP29Wd+x4yOe9aNp12MVP+nueXzTDce1/0ad6kwrTm/+ofPXIy2aVpHfctsbbfI9s2Ncct0U279mvUviuk0GAQK0/zYnuU1XtCYVnH2bjvadjvqDtjO5hDbBHwpvwlfbnakKIgRJ+slZjMzzyu3ea91nvxUxPGrHn1nUsuoeVa9u9MZnZ2PCelvd7zftUHXfMXnYdG1VtuVg1vprXiO2y5vUEeiYCBCQIfK+wvJTeDMR7ouctkZodstZV0jrLOxZ9abQOHMcTlrc35M3OIk8hzbGu1eQWcyy8bPM23quWdm7kpdGY1wtiX2uIHHdduyIy7pp7UdUeINR4pwphSXy3CSFIgD9+FiyVSBBQJYCwVJslFwQECSAswVKJBAFVAghLtVlyQUCQAMISLJVIEFAlgLBUmyUXBAQJICzBUokEAVUCCEu1WXJBQJAAwhIslUgQUCWAsFSbJRcEBAkgLMFSiQQBVQIIS7VZckFAkADCEiyVSBBQJYCwVJslFwQECSAswVKJBAFVAghLtVlyQUCQAMISLJVIEFAlgLBUmyUXBAQJICzBUokEAVUCCEu1WXJBQJAAwhIslUgQUCWAsFSbJRcEBAkgLMFSiQQBVQIIS7VZckFAkADCEiyVSBBQJYCwVJslFwQECSAswVKJBAFVAghLtVlyQUCQAMISLJVIEFAlgLBUmyUXBAQJICzBUokEAVUCCEu1WXJBQJAAwhIslUgQUCWAsFSbJRcEBAkgLMFSiQQBVQIIS7VZckFAkADCEiyVSBBQJYCwVJslFwQECSAswVKJBAFVAghLtVlyQUCQAMISLJVIEFAlgLBUmyUXBAQJICzBUokEAVUCCEu1WXJBQJAAwhIslUgQUCWAsFSbJRcEBAkgLMFSiQQBVQIIS7VZckFAkADCEiyVSBBQJYCwVJslFwQECSAswVKJBAFVAghLtVlyQUCQAMISLJVIEFAlgLBUmyUXBAQJICzBUokEAVUCCEu1WXJBQJAAwhIslUgQUCWAsFSbJRcEBAkgLMFSiQQBVQIIS7VZckFAkADCEiyVSBBQJYCwVJslFwQECSAswVKJBAFVAghLtVlyQUCQAMISLJVIEFAlgLBUmyUXBAQJICzBUokEAVUCCEu1WXJBQJAAwhIslUgQUCWAsFSbJRcEBAkgLMFSiQQBVQIIS7VZckFAkADCEiyVSBBQJYCwVJslFwQECSAswVKJBAFVAghLtVlyQUCQAMISLJVIEFAlgLBUmyUXBAQJICzBUokEAVUCCEu1WXJBQJAAwhIslUgQUCWAsFSbJRcEBAkgLMFSiQQBVQIIS7VZckFAkADCEiyVSBBQJYCwVJslFwQECSAswVKJBAFVAghLtVlyQUCQAMISLJVIEFAlgLBUmyUXBAQJICzBUokEAVUCCEu1WXJBQJAAwhIslUgQUCWAsFSbJRcEBAkgLMFSiQQBVQIIS7VZckFAkADCEiyVSBBQJYCwVJslFwQECSAswVKJBAFVAghLtVlyQUCQAMISLJVIEFAlgLBUmyUXBAQJICzBUokEAVUCCEu1WXJBQJAAwhIslUgQUCWAsFSbJRcEBAkgLMFSiQQBVQIIS7VZckFAkADCEiyVSBBQJYCwVJslFwQECSAswVKJBAFVAghLtVlyQUCQAMISLJVIEFAlgLBUmyUXBAQJICzBUokEAVUCCEu1WXJBQJAAwhIslUgQUCWAsFSbJRcEBAkgLMFSiQQBVQIIS7VZckFAkADCEiyVSBBQJYCwVJslFwQECfwfXVXO0yIn8tUAAAAASUVORK5CYII=\"\n" +
//                    "    },\n" +
//                    "}\n" +
//                    "CanvasRenderingContext2D = {\n" +
//                    "    fillRect: function () {},\n" +
//                    "    fillText: function () {}\n" +
//                    "}\n" +
//                    "localStorage = {\n" +
//                    "    removeItem: function (key) {\n" +
//                    "        delete this[key]\n" +
//                    "    },\n" +
//                    "    getItem: function (key) {\n" +
//                    "        return this[key] ? this[key]: null;\n" +
//                    "    },\n" +
//                    "    setItem: function (key, value) {\n" +
//                    "        this[key] = \"\" + value;\n" +
//                    "    },\n" +
//                    "};\n" +
//                    "sessionStorage = {}\n" +
//                    "setInterval = window.setInterval = function (){}\n" +
//                    "setInterval.toString = function(){return \"function setInterval() { [native code] }\"}\n" +
//                    "setTimeout = function (){}\n" +
//                    "top = window.top = window\n" +
////                    "global = undefined;\n" +
//                    "child_process = undefined;\n" +
//                    "closed = {\n" +
//                    "    __proto__: ( 1>>3 >4 )[\"__proto__\"]\n" +
//                    "}\n" +
////                    "function get_cookie(seed, ts, code) {\n" +
////                    "    var Buffer;\n" +
////                    "    process = undefined;\n" +
////                    "    function CustomEvent() {}\n" +
////                    "    eval(code);\n" +
////                    "    cookie = encodeURIComponent(new ABC().z(seed, parseInt(ts)+(480+new Date().getTimezoneOffset())*60*1000))\n" +
////                    "    console.log(cookie, cookie);\n" +
////                    "    return {cookie, cookie};\n" +
////                    "}\n" +
//                    "function encryption(seed, ts) {\n" +
//                    "    var code = new ABC().z(seed, parseInt(ts) + (480 + new Date().getTimezoneOffset()) * 60 * 1000);\n" +
//                    "    return encodeURIComponent(code)\n" +
//                    "}";
//
//            jsStr = jsStr.replaceAll("typeof process", "typeof child_process");
//            jsStr = core + jsStr;
//
//            Object eval = scriptEngine.eval(jsStr);
//
//            Invocable inv2 = (Invocable) scriptEngine;
//            Object encryption = inv2.invokeFunction("encryption", seed, ts);
//            return null;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private static Seed querySeed(OkHttpClient client) throws IOException {
//        Request request = new Request.Builder()
//                .url("https://www.zhipin.com/job_detail/?query=java&city=101210100&industry=&position=")
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (response.code() != 302) {
//                throw new IllegalArgumentException("The response code is not 302");
//            }
//
//            String location = URLDecoder.decode(response.header("location"));
//
//            if (location == null) {
//                throw new IllegalArgumentException("The location can't null");
//            }
//
//            Map<String, String> headMap = StringUtil.parseQueryString(location);
//
//            String seed = headMap.get("seed");
//            String name = headMap.get("name");
//            Long ts = Long.valueOf(headMap.get("ts"));
//
//            return new Seed(location, seed, name, ts);
//        }
//    }
//
//    private static void queryBossJobList(String zpStoken) throws IOException {
//        OkHttpClient client = new OkHttpClient()
//                .newBuilder()
//                .followRedirects(false)
//                .followSslRedirects(false)
//                .build();
//
//        Request request = new Request.Builder()
//                .url("https://www.zhipin.com/job_detail/?query=java&city=101210100&industry=&position=")
//                .addHeader("cookie", "__zp_stoken__=" + zpStoken + ";")
//                .build();
//
//        final Call call = client.newCall(request);
//
//
//        try (Response response = call.execute()) {
//            System.out.println(response.body().string());
//        }
//    }
//}
