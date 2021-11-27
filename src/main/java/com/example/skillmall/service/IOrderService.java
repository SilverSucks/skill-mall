package com.example.skillmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.skillmall.pojo.Order;
import com.example.skillmall.pojo.User;
import com.example.skillmall.vo.GoodsVo;

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
}
