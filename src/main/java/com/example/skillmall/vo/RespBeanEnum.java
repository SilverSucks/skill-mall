package com.example.skillmall.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @version v1.0
 * @Description 公共对象返回枚举，包括一些状态码，和常用的信息提示
 * @Date 2021-11-20 13:34
 */
@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {

    //通用的状态码
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "服务端异常"),

    //登录模块 5002xx
    //登录模块的异常提醒
    LOGIN_ERROR(500210, "用户名或密码不正确"),
    //手机号不正确提醒
    MOBILE_ERROR(500211, "手机号码格式不正确"),
    //参数校验异常
    BIND_ERROR(500212, "参数校验异常"),
    MOBILE_NOT_EXIST(500213, "手机号码不存在"),
    PASSWORD_UPDATE_FAIL(500214, "密码更新失败"),
    SESSION_ERROR(500215, "用户不存在"),

    //秒杀模块5005xx
    EMPTY_STOCK(500500, "库存不足"),
    REPEATE_ERROR(500501, "该商品每人限购一件"),

    //订单模块503xx
    ORDER_NOT_EXIST(500300, "订单信息不存在")
    ;


    //状态码
    private final Integer code;
    //常用的信息提示
    private final String message;

}















