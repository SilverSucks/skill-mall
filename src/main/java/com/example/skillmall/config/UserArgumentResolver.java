package com.example.skillmall.config;

import com.example.skillmall.pojo.User;
import com.example.skillmall.service.IUserService;
import com.example.skillmall.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version v1.0
 * @Description 用户自定义参数
 * @Date 2021-11-22 14:47
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private IUserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        //如果返回true，表示入参为User,是User的话，走下面的resolveArgument方法
        return clazz == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //通过webrequest拿到request
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        String ticket = CookieUtil.getCookieValue(request, "userTicket");

        if (!StringUtils.hasText(ticket)){  //如果ticket为空，直接返回null
            return null;
        }

        return userService.getUserByCookie(ticket, request ,response);
    }
}
