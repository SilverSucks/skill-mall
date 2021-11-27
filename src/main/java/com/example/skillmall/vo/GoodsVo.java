package com.example.skillmall.vo;

import com.example.skillmall.pojo.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @version v1.0
 * @Description 秒杀商品对象，包括了秒杀商品的详细信息
 * @Date 2021-11-23 13:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVo extends Goods {
    //秒杀价
    private BigDecimal seckillPrice;
    //库存数量
    private Integer stockCount;
    //秒杀开始时间
    private Date startDate;
    //秒杀结束时间
    private Date endDate;

}
