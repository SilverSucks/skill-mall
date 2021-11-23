package com.example.skillmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.skillmall.pojo.User;
import com.example.skillmall.vo.LoginVo;
import com.example.skillmall.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lkl
 * @since 2021-11-20
 */
public interface IUserService extends IService<User> {

    /**
     * 功能：登录
     * @param loginVo
     * @param request
     * @param response
     * @return
     */
    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    /**
     * 根据cookie获取用户
     * @param userTicket
     * @return
     */
    User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response);
}
