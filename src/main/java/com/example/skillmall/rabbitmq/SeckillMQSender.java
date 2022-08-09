package com.example.skillmall.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @version v1.0
 * @Description 秒杀 消息发送类
 * @Date 2021-11-29 10:03
 */
@Service
@Slf4j
public class SeckillMQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送秒杀信息
     * @param message
     */
    public void sendsecKillMessage(String message) {
        log.info("发送消息：" + message);
        rabbitTemplate.convertAndSend("seckillExchange", "seckill.msg", message);
    }
}
