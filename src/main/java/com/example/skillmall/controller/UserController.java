package com.example.skillmall.controller;


import com.example.skillmall.pojo.User;
import com.example.skillmall.rabbitmq.MQSender;
import com.example.skillmall.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lkl
 * @since 2021-11-20
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MQSender mqSender;
    /**
     * 功能描述：用户信息（测试）
     * @param user
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public RespBean info(User user){
        return RespBean.success(user);
    }

    /**
     * 功能描述：测试发送RabbitMQ消息
     */
    @RequestMapping("/mq")
    @ResponseBody
    public void mq(){
        mqSender.send01("Hello");
    }

    /**
     * 功能描述：fanout模式发送消息
     */
    @RequestMapping("/mq/fanout")
    @ResponseBody
    public void mq01(){
        mqSender.send01("Hello");
    }

    /**
     * 测试direct交换机发送消息的两个方法 mq02()和 mq03()
     */
    @RequestMapping("/mq/direct01")
    @ResponseBody
    public void mq02(){
        mqSender.send02("Hello,Red");
    }

    @RequestMapping("/mq/direct02")
    @ResponseBody
    public void mq03(){
        mqSender.send03("Hello,Green");
    }
    /**
     * 测试topic交换机发送消息的两个方法 mq04() 和 mq05()
     */
    @RequestMapping("/mq/topic01")
    @ResponseBody
    public void mq04(){
        mqSender.send04("Hello,Red");
    }

    @RequestMapping("/mq/topic02")
    @ResponseBody
    public void mq05(){
        mqSender.send05("Hello, Green");
    }

    /**
     * 测试headers交换机发送消息的两个方法 mq06() 和 mq07()
     */
    @RequestMapping("/mq/header01")
    @ResponseBody
    public void mq06(){
        mqSender.send6("Hello,header01");
    }
    @RequestMapping("/mq/header02")
    @ResponseBody
    public void mq07(){
        mqSender.send07("Hello,header02");
    }

}









