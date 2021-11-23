package com.example.skillmall.controller;

import com.example.skillmall.service.IUserService;
import com.example.skillmall.vo.LoginVo;
import com.example.skillmall.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * @version v1.0
 * @Description TODO
 * @Date 2021-11-20 11:04
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private IUserService userService;

    /**
     * 跳转登录页面
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    /**
     * 登录功能
     * @param loginVo
     * @return
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public RespBean doLogin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response){
//        log.info("{}",loginVo);
        return userService.doLogin(loginVo, request, response);
    }

}
