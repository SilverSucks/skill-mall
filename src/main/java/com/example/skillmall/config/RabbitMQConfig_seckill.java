package com.example.skillmall.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version v1.0
 * @Description 有关商品下单的 RabbitMQ 的配置类
 * @Date 2021-11-29 9:53
 */
@Configuration
public class RabbitMQConfig_seckill {

    private static final String SECKILL_QUEUE = "seckillQueue";
    private static final String SECKILL_EXCHANGE = "seckillExchange";

    @Bean
    public Queue seckill_queue(){
        return new Queue(SECKILL_QUEUE);
    }
    /**
     * 自定义topic交换机
     */
    @Bean
    public TopicExchange seckill_topicExchange(){
        return new TopicExchange(SECKILL_EXCHANGE);
    }
    @Bean
    public Binding seckill_binding01(){
        return BindingBuilder.bind(seckill_queue())
                .to(seckill_topicExchange())
                .with("seckill.#");
    }
}
