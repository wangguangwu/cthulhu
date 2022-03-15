package com.wangguangwu.controller;

import com.wangguangwu.client.entity.ZhipinData;
import com.wangguangwu.client.startup.Bootstrap;
import com.wangguangwu.entity.CthulhuVO;
import com.wangguangwu.service.CthulhuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RuntimeUtil;
import lombok.extern.slf4j.Slf4j;

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
        List<ZhipinData> data
                = bootstrap.url("https://www.zhipin.com/job_detail/?query=java&city=101210100&industry=&position=").start();
        data.forEach(System.out::println);
//        cthulhuService.saveData(data);
    }

    @RequestMapping("queryData")
    public String queryData() {
        return cthulhuService.queryData();
    }

}
