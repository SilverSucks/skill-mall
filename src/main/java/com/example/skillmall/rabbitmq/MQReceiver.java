package com.example.skillmall.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @version v1.0
 * @Description 消息的消费者
 * @Date 2021-11-28 12:32
 */
@Service
@Slf4j
public class MQReceiver {
//    @RabbitListener(queues = "queue")
//    public void receive(Object msg) {
//        log.info("接收消息:" + msg);
//    }

    @RabbitListener(queues = "queue_fanout01")
    public void receive01(Object msg) {
        log.info("QUEUE01接收消息：" + msg);
    }

    @RabbitListener(queues = "queue_fanout02")
    public void receive02(Object msg) {
        log.info("QUEUE02接收消息：" + msg);
    }

    @RabbitListener(queues = "queue_direct01")
    public void receive03(Object msg) {
        log.info("测试direct交换机，QUEUE01接收消息：" + msg);
    }

    @RabbitListener(queues = "queue_direct02")
    public void receive04(Object msg) {
        log.info("测试direct交换机，QUEUE02接收消息：" + msg);
    }

    @RabbitListener(queues = "queue_topic01")
    public void receive05(Object msg) {
        log.info("测试topic交换机,QUEUE01接收消息：" + msg);
    }

    @RabbitListener(queues = "queue_topic02")
    public void receive06(Object msg) {
        log.info("测试topic交换机,QUEUE02接收消息：" + msg);
    }

    @RabbitListener(queues = "queue_header01")
    public void receive07(Message message) {
        log.info("测试headers交换机，QUEUE01接收Message对象：" + message);
        log.info("测试headers交换机，QUEUE01接收消息："+new String(message.getBody()));
    }

    @RabbitListener(queues = "queue_header02")
    public void receive08(Message message) {
        log.info("测试headers交换机，QUEUE02接收Message对象：" + message);
        log.info("测试headers交换机，QUEUE02接收消息："+new String(message.getBody()));
    }
}











