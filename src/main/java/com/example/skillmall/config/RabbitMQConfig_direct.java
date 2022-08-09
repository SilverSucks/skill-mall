package com.example.skillmall.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * @version v1.0
 * @Description RabbitMQ配置类  direct交换机
 * @Date 2021-11-28 16:28
 */
@Configuration
public class RabbitMQConfig_direct {

    private static final String QUEUE_DIRECT_01 = "queue_direct01";
    private static final String QUEUE_DIRECT_02 = "queue_direct02";
    private static final String DIRECT_EXCHANGE = "directExchange";
    private static final String ROUTINGKEY01 = "queue.red";
    private static final String ROUTINGKEY02 = "queue.green";

    @Bean
    public Queue direct_queue01(){
        return new Queue(QUEUE_DIRECT_01);
    }
    @Bean
    public Queue direct_queue02(){
        return new Queue(QUEUE_DIRECT_02);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(DIRECT_EXCHANGE);
    }
    @Bean
    public Binding direct_binding01(){
        return BindingBuilder.bind(direct_queue01()).to(directExchange()).with(ROUTINGKEY01);
    }
    @Bean
    public Binding direct_binding02(){
        return BindingBuilder.bind(direct_queue02()).to(directExchange()).with(ROUTINGKEY02);
    }
}
