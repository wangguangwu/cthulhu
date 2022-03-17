package com.wangguangwu.controller;

import com.wangguangwu.client.startup.Bootstrap;
import com.wangguangwu.entity.CthulhuVO;
import com.wangguangwu.entity.ZhipinData;
import com.wangguangwu.service.CthulhuService;
import com.wangguangwu.utils.BossUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RuntimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Project entry.
 *
 * @author wangguangwu
 */
@Slf4j
@RestController
@RequestMapping("cthulhu")
public class CthulhuController {

    private final CthulhuService cthulhuService;

    @Autowired
    public CthulhuController(CthulhuService cthulhuService) {
        this.cthulhuService = cthulhuService;
    }

    @RequestMapping("helloWorld")
    public String sendResponse() {
        String response = "<h1>HelloWorld</h1>";
        log.info("response: {}", response);
        return response;
    }

    @RequestMapping("helloCthulhu")
    public CthulhuVO cthulhu(@RequestParam("name") String name, int age) {
        log.info("name: {}", name);
        log.info("age: {}", age);
        return new CthulhuVO(name, age);
    }

    @RequestMapping("restart")
    public String restart() {
        // The absolute path of the script on the server.
        Process exec = RuntimeUtil.exec("bash /root/workspace/deploy.sh");
        log.info("outputStream: {}", IoUtil.read(exec.getInputStream()));
        log.error("errorStream: {}", IoUtil.read(exec.getErrorStream()));
        return "Restart server success";
    }

    @RequestMapping("crawData")
    public void crawData() {
        Bootstrap bootstrap = new Bootstrap();
        List<ZhipinData> data = new ArrayList<>();
        BossUtil bossUtil = new BossUtil();
        String url = "https://www.zhipin.com/c101210100/?query=java&page=2&ka=page-1";
        String responseBody = bootstrap
                .url(url)
                .cookies(bossUtil.getZpsToken(url)).start();

        System.out.println(responseBody);
//        List<String> list = Arrays.asList("https://www.zhipin.com/c101210100/?query=java&page=2&ka=page-1",
//                "https://www.zhipin.com/c101210100/?query=java&page=2&ka=page-2",
//                "https://www.zhipin.com/c101210100/?query=java&page=3&ka=page-3",
//                "https://www.zhipin.com/c101210100/?query=java&page=3&ka=page-4",
//                "https://www.zhipin.com/c101210100/?query=java&page=3&ka=page-5",
//                "https://www.zhipin.com/c101210100/?query=java&page=3&ka=page-6",
//                "https://www.zhipin.com/c101210100/?query=java&page=3&ka=page-7",
//                "https://www.zhipin.com/c101210100/?query=java&page=3&ka=page-8",
//                "https://www.zhipin.com/c101210100/?query=java&page=3&ka=page-9",
//                "https://www.zhipin.com/c101210100/?query=java&page=3&ka=page-10",
//                "https://www.zhipin.com/c101210100/?query=java&page=3&ka=page-11",
//                "https://www.zhipin.com/c101210100/?query=java&page=3&ka=page-12",
//                "https://www.zhipin.com/c101210100/?query=java&page=3&ka=page-13",
//                "https://www.zhipin.com/c101210100/?query=java&page=3&ka=page-14",
//                "https://www.zhipin.com/c101210100/?query=java&page=3&ka=page-15",
//                "https://www.zhipin.com/c101210100/?query=java&page=3&ka=page-16",
//                "https://www.zhipin.com/c101210100/?query=java&page=3&ka=page-17",
//                "https://www.zhipin.com/c101210100/?query=java&page=3&ka=page-18",
//                "https://www.zhipin.com/c101210100/?query=java&page=3&ka=page-19",
//                "https://www.zhipin.com/c101210100/?query=java&page=3&ka=page-20");
//        list.forEach(url -> {
//            String responseBody = bootstrap.url(url).start();
//            data.addAll(cthulhuService.analysisData(responseBody));
//            try {
//                Thread.sleep(30000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        cthulhuService.saveData(data);
    }

    @RequestMapping("queryData")
    public String queryData() {
        return cthulhuService.queryData();
    }

    @RequestMapping("test")
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
