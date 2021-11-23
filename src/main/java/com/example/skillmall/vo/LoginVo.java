package com.example.skillmall.vo;

import com.example.skillmall.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @version v1.0
 * @Description 登录参数
 * @Date 2021-11-20 15:06
 */
@Data
public class LoginVo {
    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;

}
