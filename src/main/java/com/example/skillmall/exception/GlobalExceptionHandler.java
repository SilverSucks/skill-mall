package com.example.skillmall.exception;

import com.example.skillmall.vo.RespBean;
import com.example.skillmall.vo.RespBeanEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @version v1.0
 * @Description 全局异常处理类
 * @Date 2021-11-21 14:26
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public RespBean ExceptionHandler(Exception e){
        if (e instanceof GlobalException){ //如果异常属于全局异常
            //把全局
            GlobalException ex = (GlobalException) e;
            return RespBean.error(ex.getRespBeanEnum());
        }else if (e instanceof BindException){
            BindException ex = (BindException) e;
            RespBean respBean = RespBean.error(RespBeanEnum.BIND_ERROR);

            //拿到绑定异常抛出来的信息
            respBean.setMessage("参数校验异常：" +
                    ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return respBean;
        }
        return RespBean.error(RespBeanEnum.ERROR);
    }
}
