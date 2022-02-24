package com.wangguangwu.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import com.wangguangwu.CthulhuCrawApplication;

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
    public String restart() throws IOException {
        // the absolute path of the script on the server
//        String bashCommand = "bash /root/workspace/deploy.sh";
//        Runtime.getRuntime().exec(bashCommand);

        // todo test
        CthulhuCrawApplication.killSelf();
        return "Restart server success";
    }

}
