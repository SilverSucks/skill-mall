package com.example.skillmall.rabbitmq;

import com.example.skillmall.pojo.SeckillMessage;
import com.example.skillmall.pojo.User;
import com.example.skillmall.service.IGoodsService;
import com.example.skillmall.service.IOrderService;

import com.example.skillmall.utils.JsonUtil;
import com.example.skillmall.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @version v1.0
 * @Description 秒杀，消息接收者（消息消费者）
 * @Date 2021-11-29 10:10
 */
@Service
@Slf4j
public class SeckillMQReceiver {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IOrderService orderService;

    /**
     * 下单操作
     * @param msg
     */
    @RabbitListener(queues = "seckillQueue")
    public void receive(String msg){
        log.info("QUEUE接收消息"+msg);
        //通过JsonUtil，把msg转为消息对象
        SeckillMessage message = JsonUtil.jsonStr2Object(msg, SeckillMessage.class);
        Long goodsId = message.getGoodsId();
        User user = message.getUser();
        GoodsVo goods = goodsService.findGoodsByGoodsId(goodsId);
        //判断库存
        if (goods.getStockCount() < 1){
            return;
        }
        //判断是否重复抢购
        String seckillOrderJson = (String) redisTemplate.opsForValue()
                .get("order:" + user.getId() + ":" + goodsId);
        if (!StringUtils.isEmpty(seckillOrderJson)) {
            return;
        }
        orderService.seckill(user, goods);
    }
}
















