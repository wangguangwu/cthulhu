package com.wangguangwu.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
    public String restart() {
        // the absolute path of the script on the server
        String bashCommand = "/root/workspace/deploy.sh";
        Runtime runtime = Runtime.getRuntime();
        int status = 0;
        String response = "restart server success";
        try {
            // call the script
            Process pro = runtime.exec(bashCommand);
            // call waitFor to wait for the script finish executing
            // otherwise, the script will fail
            status = pro.waitFor();
        } catch (InterruptedException | IOException e) {
            log.error("CthulhuController restart error: ", e);
        }
        // 0: the code represent the script is executed successfully
        if (status != 0) {
            response = "restart server error";
            log.error(response);
        }
        log.info(response);
        return response;
    }

}
