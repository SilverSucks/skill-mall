package com.example.skillmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version v1.0
 * @Description 秒杀信息 里面封装了秒杀用户user和商品id goodsId
 * @Date 2021-11-29 9:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillMessage {
    private User user;
    private Long goodsId;
}


