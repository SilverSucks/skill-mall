package com.example.skillmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.skillmall.pojo.SeckillOrder;
import com.example.skillmall.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lkl
 * @since 2021-11-23
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId
     * @return 如果有orderId，表示秒杀成功；如果是-1，秒杀失败；0表示排队中
     */
    Long getResult(User user, Long goodsId);
}
