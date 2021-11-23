package com.example.skillmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @version v1.0
 * @Description Redis配置类，功能实现对象序列化
 * @Date 2021-11-22 10:04
 */
//组合注解@Configuration
@Configuration
public class RedisConfig {

    /**
     * 实现序列化，先准备一个RedisTemplate，key一般都是String类型， value是Object类型
     *
     * @param connectionFactory 需要一个连接工厂
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        //首先先拿到redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //key序列器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //value序列器
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //Hash类型 key序列器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //Hash类型 value序列器
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        //设置连接工厂
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
}
