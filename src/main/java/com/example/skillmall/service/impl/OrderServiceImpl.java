package com.example.skillmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.skillmall.exception.GlobalException;
import com.example.skillmall.mapper.OrderMapper;
import com.example.skillmall.pojo.Order;
import com.example.skillmall.pojo.SeckillGoods;
import com.example.skillmall.pojo.SeckillOrder;
import com.example.skillmall.pojo.User;
import com.example.skillmall.service.IGoodsService;
import com.example.skillmall.service.IOrderService;
import com.example.skillmall.service.ISeckillGoodsService;
import com.example.skillmall.service.ISeckillOrderService;
import com.example.skillmall.vo.GoodsVo;
import com.example.skillmall.vo.OrderDetailVo;
import com.example.skillmall.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lkl
 * @since 2021-11-23
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 秒杀
     *
     * @param user
     * @param goods
     * @return
     */
    @Transactional
    @Override
    public Order seckill(User user, GoodsVo goods) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //第一步，秒杀商品表减库存
        //先拿到秒杀商品
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>()
                .eq("goods_id", goods.getId()));
        // 然后减库存
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        //同时更新商品表
//        seckillGoodsService.updateById(seckillGoods);
        //解决秒杀超卖问题，做如下修改:
        // 在更新库存的时候，用SQL语句做处理，同时判断的时候加了一个库存必须大于0才会操作
        boolean result = seckillGoodsService.update(
                new UpdateWrapper<SeckillGoods>()
                        .setSql("stock_count = " + "stock_count-1")
                        .eq("goods_id", goods.getId())
                        .gt("stock_count", 0)
        );

        if (seckillGoods.getStockCount() < 1){
            //判断是否还有库存
            valueOperations.set("isStockEmpty:" + goods.getId(), "0");
            return null;
        }

//        //如果返回false，更新失败，则不创建订单
//        if (!result){
//            return null;
//        }

        //第二步，生成订单
        Order order = new Order();
        //订单Id自动生成,不需要我们管
        //用户Id
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        //收货地址Id
        order.setDeliveryAddrId(0L);
        //商品名称
        order.setGoodsName(goods.getGoodsName());
        //数量1
        order.setGoodsCount(1);
        //价格，秒杀商品的价格
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        //订单状态：创建未支付
        order.setStatus(0);
        //日期：当前的日期
        order.setCreateDate(new Date());
        //创建未支付，没有付款日期
//        order.setPayDate();

        //向数据库中插入数据
        orderMapper.insert(order);
        /**
         * 因为订单中有一个秒杀订单Id,所以先生成订单信息， 然后再生成秒杀订单信息
         */
        //第三步，生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        //同样的，id自动生成，不用管
//        seckillOrder.setId();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrderService.save(seckillOrder);
        //最后把订单返回
        return order;
    }

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    @Override
    public OrderDetailVo detail(Long orderId) {
        if (orderId == null){
            throw new GlobalException(RespBeanEnum.ORDER_NOT_EXIST);
        }
        Order order = orderMapper.selectById(orderId);
        GoodsVo goodsVo = goodsService.findGoodsByGoodsId(order.getGoodsId());
        OrderDetailVo detail = new OrderDetailVo();
        detail.setOrder(order);
        detail.setGoodsVo(goodsVo);
        return detail;
    }
}

















