package com.example.skillmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.skillmall.pojo.Goods;
import com.example.skillmall.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lkl
 * @since 2021-11-23
 */
public interface IGoodsService extends IService<Goods> {
    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    /**
     * 获取商品详情
     * @param goodsId
     * @return
     */
    GoodsVo findGoodsByGoodsId(Long goodsId);
}
