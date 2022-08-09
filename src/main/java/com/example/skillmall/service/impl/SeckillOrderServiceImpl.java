package com.example.skillmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.skillmall.mapper.SeckillOrderMapper;
import com.example.skillmall.pojo.SeckillOrder;
import com.example.skillmall.pojo.User;
import com.example.skillmall.service.ISeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lkl
 * @since 2021-11-23
 */
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder>
        implements ISeckillOrderService {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId
     * @return 如果有orderId，表示秒杀成功；如果是-1，秒杀失败；0表示排队中
     */
    @Override
    public Long getResult(User user, Long goodsId) {
        SeckillOrder seckillOrder = seckillOrderMapper.selectOne(
                new QueryWrapper<SeckillOrder>()
                        .eq("user_id", user.getId())
                        .eq("goods_id", goodsId)
        );
        if (null != seckillOrder){
            return seckillOrder.getId();
        } else{
            if (redisTemplate.hasKey("isStockEmpty:" + goodsId)){
                return -1L;
            } else {
                return 0L;
            }
        }
    }
}



















