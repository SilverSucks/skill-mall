package com.example.skillmall.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @version v1.0
 * @Description TODO
 * @Date 2021-11-19 21:12
 */
@Controller
@RequestMapping("/demo")
public class TestController {
    //做一个简单的页面跳转
    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("name", "jack");
        return "hello";
    }
}
