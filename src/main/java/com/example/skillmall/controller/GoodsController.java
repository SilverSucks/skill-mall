package com.example.skillmall.controller;

import com.example.skillmall.pojo.User;
import com.example.skillmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @version v1.0
 * @Description 跳转到商品详情页
 * @Date 2021-11-21 18:31
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IUserService userService;


//    @RequestMapping("/toList")
//    public String toList(HttpServletRequest request, HttpServletResponse response,
//                         Model model, @CookieValue("userTicket") String ticket){
//        //通过session获取用户，判断用户是否存在，如果用户不存在，将其跳回到登录页面
//        if (!StringUtils.hasLength(ticket)){
//            return "login";
//        }
////        User user = (User) session.getAttribute(ticket);
//        User user = userService.getUserByCookie(ticket, request, response);
//        if (null == user){
//            return "login";
//        }
//        //如果都没有问题，把user对象传到前端页面
//        model.addAttribute("user", user);
//        return "goodsList";
//    }

    @RequestMapping("/toList")
    public String toList(Model model, User user){

        model.addAttribute("user", user);
        return "goodsList";
    }
}
