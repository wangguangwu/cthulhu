package com.wangguangwu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RuntimeUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Project entry.
 *
 * @author wangguangwu
 * @date 2022/2/23
 */
@Slf4j
@RestController
@RequestMapping("cthulhu")
public class CthulhuController {

    @RequestMapping("helloWorld")
    public String sendResponse() {
        String response = "<h1>HelloWorld</h1>";
        log.info("response: {}", response);
        return response;
    }

    @RequestMapping("restart")
    public String restart() throws InterruptedException {
        // The absolute path of the script on the server.
        Process exec = RuntimeUtil.exec("bash /root/workspace/deploy.sh");
        log.info("outputStream: {}", IoUtil.read(exec.getInputStream()));
        log.error("errorStream: {}", IoUtil.read(exec.getErrorStream()));
        return "Restart server success";
    }

}
