package com.example.skillmall.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version v1.0
 * @Description Topic交换机
 * @Date 2021-11-28 19:48
 */
@Configuration
public class RabbitMQConfig_topic {
    private static final String QUEUE_TOPIC_01 = "queue_topic01";
    private static final String QUEUE_TOPIC_02 = "queue_topic02";
    private static final String TOPIC_EXCHANGE = "topicExchange";
    private static final String ROUTINGKEY01 = "#.queue.#";
    private static final String ROUTINGKEY02 = "*.queue.#";

    @Bean
    public Queue topic_queue01(){
        return new Queue(QUEUE_TOPIC_01);
    }
    @Bean
    public Queue topic_queue02(){
        return new Queue(QUEUE_TOPIC_02);
    }
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding topic_binding01(){
        return BindingBuilder.bind(topic_queue01()).to(topicExchange()).with(ROUTINGKEY01);
    }
    @Bean
    public Binding topic_binding02(){
        return BindingBuilder.bind(topic_queue02()).to(topicExchange()).with(ROUTINGKEY02);
    }
}
