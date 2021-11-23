package com.example.skillmall.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version v1.0
 * @Description 手机号码校验类  正则表达式的方式
 * @Date 2021-11-20 18:55
 */
public class ValidatorUtil {
    private static final Pattern mobile_pattern = Pattern.compile("^1[3|4|5|8]\\d{9}$");
    public static boolean isMobile(String mobile){
        //首先判断手机号码是否为空，如果为空，直接返回false
        if (!StringUtils.hasLength(mobile)){   //号码为空
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(mobile);
        return matcher.matches();
    }

}
