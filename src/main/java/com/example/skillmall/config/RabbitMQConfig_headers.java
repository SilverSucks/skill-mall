package com.example.skillmall.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @version v1.0
 * @Description headers交换机配置类
 * @Date 2021-11-28 21:33
 */
@Configuration
public class RabbitMQConfig_headers {
    private static final String HEADERS_QUEUE01 = "queue_header01";
    private static final String HEADERS_QUEUE02 = "queue_header02";
    private static final String HEADERS_EXCHANGE = "headersExchange";

    @Bean
    public Queue headers_queue01(){
        return new Queue(HEADERS_QUEUE01);
    }
    @Bean
    public Queue headers_queue02(){
        return new Queue(HEADERS_QUEUE02);
    }
    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange(HEADERS_EXCHANGE);
    }
    @Bean
    public Binding headers_binding01(){
        Map<String, Object> map = new HashMap<>();
        map.put("color", "red");
        map.put("speed", "low");
        return BindingBuilder.bind(headers_queue01())
                .to(headersExchange())
                .whereAny(map)
                .match();
    }
    @Bean
    public Binding headers_binding02(){
        Map<String, Object> map = new HashMap<>();
        map.put("color", "red");
        map.put("speed", "fast");
        return BindingBuilder.bind(headers_queue02())
                .to(headersExchange())
                .whereAll(map)
                .match();
    }
}
