package com.wangguangwu.controller;

import java.io.InputStream;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        RuntimeUtil.exec("bash /root/workspace/deploy.sh").waitFor();
        return "Restart server success";
    }

}
