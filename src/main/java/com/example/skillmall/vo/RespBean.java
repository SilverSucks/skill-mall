package com.example.skillmall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version v1.0
 * @Description 公共返回对象
 * @Date 2021-11-20 13:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {

    //状态码
    private long code;
    //返回消息
    private String message;
    //有时返回可能带一个对象obj
    private Object obj;

    //成功返回结果
    public static RespBean success(){
        return new RespBean(
                RespBeanEnum.SUCCESS.getCode(),
                RespBeanEnum.SUCCESS.getMessage(),
                null);
    }
    //重载的success
    public static RespBean success(Object obj){
        return new RespBean(
                RespBeanEnum.SUCCESS.getCode(),
                RespBeanEnum.SUCCESS.getMessage(),
                obj);
    }

    /**
     * 返回失败结果
     * @param respBeanEnum 枚举类型
     * @return
     * 为什么要传一个枚举类型呢？
     * 因为成功都是返回200，失败各有不同403,404,500等
     */
    public static RespBean error(RespBeanEnum respBeanEnum){
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), null);
    }
    public static RespBean error(RespBeanEnum respBeanEnum, Object obj){
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), obj);
    }
}
