package com.example.skillmall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.skillmall.exception.GlobalException;
import com.example.skillmall.mapper.UserMapper;
import com.example.skillmall.pojo.User;
import com.example.skillmall.service.IUserService;
import com.example.skillmall.utils.CookieUtil;
import com.example.skillmall.utils.MD5Util;
import com.example.skillmall.utils.UUIDUtil;
import com.example.skillmall.vo.LoginVo;
import com.example.skillmall.vo.RespBean;
import com.example.skillmall.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lkl
 * @since 2021-11-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    //注入redisTemplate
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 书写登录逻辑
     * @param loginVo
     * @param request
     * @param response
     * @return
     * 前提已经通过手动插入了一条数据：
     * id          用户名  存入数据库的密码                       盐
     * 18236589635 jack   a05920fa0eea9312b4f8622a1da119c0   1a2b3c4d
     */
    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //即使前端做了校验，后端也要做校验，后端是存入数据库的最后一道防线。
//        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
//            //如果手机号或密码为空
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//        }
//        //除了上面的判断，还要判断手机号码是否符合格式
//        if (!ValidatorUtil.isMobile(mobile)){ //如果手机校验不通过
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//        }
        //如果以上都没有问题，就去数据库查询是否包含该用户信息
        //通过mobile获取到user
        User user = userMapper.selectById(mobile);
        if (null == user){  //如果查不到用户
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);

            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //如果查到了该用户，下一步校验密码
        //用户输入的密码经前端传到业务层，已经有一次MD5加密。这里的盐是存入数据库的
        //这里判断的是，用户登录输入的密码经过第一次MD5的结果password_1传入业务层，
        // 业务层需要判断password_1再经过数据库中的随机盐进行第二次MD5的结果password_2，是不是跟数据中存的一致
        if (!MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())){
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);

            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //生成Cookie
        String ticket = UUIDUtil.uuid();
        //将用户信息存入redis中
        redisTemplate.opsForValue().set("user:" + ticket, user);

        //把生成的Cookie和用户 存入session里面
        request.getSession().setAttribute(ticket, user);
        CookieUtil.setCookie(request, response, "userTicket", ticket);

        //如果正确，返回成功
        return RespBean.success();
    }

    @Override
    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        //判断userTicket是否为空
        if (!StringUtils.hasLength(userTicket)){  //如果userTicket为空，直接返回空
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
        if (user != null){
            CookieUtil.setCookie(request, response, "userTicket", userTicket);
        }
        return user;
    }
}
