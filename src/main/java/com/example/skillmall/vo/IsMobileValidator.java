package com.example.skillmall.vo;

import com.example.skillmall.utils.ValidatorUtil;
import com.example.skillmall.validator.IsMobile;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @version v1.0
 * @Description 定义规则类
 * @Date 2021-11-20 21:40
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    //手机号必填属性
    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (required){  //如果是必填,验证下手机号
            return ValidatorUtil.isMobile(value);
        }else {  //如果是非必填
            if (StringUtils.hasLength(value)){  //如果为空，返回true即可
                return true;
            }else {  //非必填的基础上，填了手机号，那也要验证下
                return ValidatorUtil.isMobile(value);
            }
        }
    }
}
