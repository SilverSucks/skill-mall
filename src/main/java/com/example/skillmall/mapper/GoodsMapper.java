package com.example.skillmall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.skillmall.pojo.Goods;
import com.example.skillmall.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lkl
 * @since 2021-11-23
 */
public interface GoodsMapper extends BaseMapper<Goods> {

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
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
