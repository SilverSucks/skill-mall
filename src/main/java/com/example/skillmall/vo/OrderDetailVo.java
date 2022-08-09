package com.example.skillmall.vo;

import com.example.skillmall.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version v1.0
 * @Description 订单详情返回对象
 * @Date 2021-11-27 19:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVo {
    private Order order;

    private GoodsVo goodsVo;
}
