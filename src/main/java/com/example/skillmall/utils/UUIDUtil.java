package com.example.skillmall.utils;

import java.util.UUID;

/**
 * @version v1.0
 * @Description TODO
 * @Date 2021-11-21 16:57
 */
public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
