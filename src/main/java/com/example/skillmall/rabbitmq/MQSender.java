package com.example.skillmall.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version v1.0
 * @Description 消息发送者
 * @Date 2021-11-28 12:28
 */
@Service
@Slf4j
public class MQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send01(Object msg) {
        log.info("发送消息" + msg);
        rabbitTemplate.convertAndSend("fanoutExchange", "", msg);
    }

    /*
    send02和send03方法用于测试，direct交换机的使用
     */

    public void send02(Object msg) {
        log.info("direct交换机测试，发送red消息：" + msg);
        rabbitTemplate.convertAndSend("directExchange", "queue.red", msg);
    }


    public void send03(Object msg) {
        log.info("direct交换机测试，发送green消息：" + msg);
        rabbitTemplate.convertAndSend("directExchange", "queue.green", msg);
    }

    /**
     * send04和send05方法用于测试，topic交换机的使用
     */
    public void send04(Object msg) {
        log.info("topic交换机测试，发送消息(被01队列接收)" + msg);
        rabbitTemplate.convertAndSend("topicExchange", "queue.red.message", msg);
    }

    public void send05(Object msg) {
        log.info("topic交换机测试，发送消息(被两个queue接收)：" + msg);
        rabbitTemplate.convertAndSend("topicExchange", "message.queue.green.abc", msg);
    }

    /**
     * send06 和 send07方法用于测试 headers交换机
     */
    public void send6(String msg) {
        log.info("headers交换机测试，发送消息(被两个queue接收):" + msg);
        MessageProperties properties = new MessageProperties();
        properties.setHeader("color", "red");
        properties.setHeader("speed", "fast");
        Message message = new Message(msg.getBytes(), properties);
        rabbitTemplate.convertAndSend("headersExchange", "", message);
    }

    public void send07(String msg) {
        log.info("测试headers交换机，发送消息(被01队列接收):"+msg);
        MessageProperties properties = new MessageProperties();
        properties.setHeader("color", "red");
        properties.setHeader("speed", "normal");
        Message message = new Message(msg.getBytes(), properties);
        rabbitTemplate.convertAndSend("headersExchange", "", message);
    }

    public void sendsecKillMessage(String msg){
        log.info("发送消息：" + msg);
        rabbitTemplate.convertAndSend("seckillExchange", "seckill.msg", msg);
    }
}



