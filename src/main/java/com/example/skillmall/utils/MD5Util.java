package com.example.skillmall.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @version v1.0
 * @Description MD5工具类
 * @Date 2021-11-19 22:21
 */
public class MD5Util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    //第一个盐，这个盐是为了跟前端同一，第一次加密在前端加密
    private static final String salt = "1a2b3c4d";

    //第一次加密:用户输入的密码，转为后端接收的密码
    public static String inputPassToFormPass(String inputPass){
        //对盐做一个混淆，随机选择
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }
    //第二次加密,第二次加密的盐是存到数据库中的
    public static String formPassToDBPass(String formPass, String salt){
        String str = "" + salt.charAt(2) + salt.charAt(1) + formPass + salt.charAt(3) + salt.charAt(6);
        return md5(str);
    }

    //后端真正调用的方法
    /**
     * @param inputPass 用户输入的明文密码
     * @param salt   随机盐
     * @return
     */
    public static String inputPassToDBPass(String inputPass, String salt){
        String fromPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(fromPass, salt);
        return dbPass;
    }

    //写一个main方法，测试一下自己写加密方法
    public static void main(String[] args) {
        String password_1 = inputPassToFormPass("111111");
        String password_2 = formPassToDBPass(password_1, "1a2b3c4d");

        String password_final = inputPassToDBPass("111111", "1a2b3c4d");
        System.out.println("明文--->后端接收密码：" + password_1);
        System.out.println("后端接收密码--->存入数据库的密码："+ password_2);
        System.out.println("明文--->存入数据库的密码："+ password_final);
    }

}
