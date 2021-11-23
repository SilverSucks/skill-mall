package com.example.skillmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.skillmall.mapper")
public class SkillMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkillMallApplication.class, args);
    }

}
