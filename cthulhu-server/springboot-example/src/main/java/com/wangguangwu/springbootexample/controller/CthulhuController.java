package com.wangguangwu.springbootexample.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangguangwu
 * @date 2022/2/27
 */
@RestController
@RequestMapping("/cthulhu")
public class CthulhuController {

    @RequestMapping("helloWorld")
    public String HelloWorld () {
        return "<h1>HelloWorld</h1>";
    }

}
