package com.example.skillmall;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class SkillMallApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void testLock01(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //占位，如果key 不存在才可以设置成功
        Boolean isLock = valueOperations.setIfAbsent("k1", "v1");
        //如果占位成功，进行正常操作
        if (isLock){
            valueOperations.set("name", "xxxx");
            String name = (String) valueOperations.get("name");
            System.out.println(name);
            //操作结束，删除锁
            redisTemplate.delete("k1");
        }else{
            System.out.println("有线程在使用，请稍后");
        }
    }
    @Test
    public void testLock02(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //设置锁的超时时间5s
        Boolean isLock = valueOperations.setIfAbsent("k1", "v1");
        if (isLock){
            valueOperations.set("name", "jack");
            String name = (String) valueOperations.get("name");
            System.out.println(name);
            //手动模拟一个异常
            Integer.parseInt("asdfasdf");
            redisTemplate.delete("k1");
        }else {
            System.out.println("有线程在使用，请稍后");
        }
    }
}
