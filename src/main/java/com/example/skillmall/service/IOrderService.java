package com.example.skillmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.skillmall.pojo.Order;
import com.example.skillmall.pojo.User;
import com.example.skillmall.vo.GoodsVo;
import com.example.skillmall.vo.OrderDetailVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lkl
 * @since 2021-11-23
 */
public interface IOrderService extends IService<Order> {

    /**
     * 秒杀
     * @param user
     * @param goods
     * @return
     */
    Order seckill(User user, GoodsVo goods);

    /**
     * 功能描述：订单详情
     * @param orderId
     * @return
     */
    OrderDetailVo detail(Long orderId);
}
