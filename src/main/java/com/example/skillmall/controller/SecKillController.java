package com.example.skillmall.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.skillmall.pojo.Order;
import com.example.skillmall.pojo.SeckillOrder;
import com.example.skillmall.pojo.User;
import com.example.skillmall.service.IGoodsService;
import com.example.skillmall.service.IOrderService;
import com.example.skillmall.service.ISeckillOrderService;
import com.example.skillmall.vo.GoodsVo;
import com.example.skillmall.vo.RespBean;
import com.example.skillmall.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @version v1.0
 * @Description TODO
 * @Date 2021-11-24 8:58
 */

@Controller
@RequestMapping("/seckill")
public class SecKillController {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IOrderService orderService;

    @RequestMapping("/doSeckill2")
    public String doSecKill2(Model model, User user, Long goodsId) {
        if (null == user) {
            //用户为空，返回登录页面
            return "login";
        }
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.findGoodsByGoodsId(goodsId);
        //判断库存
        if (goods.getStockCount() < 1) {
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return "secKillFail";
        }
        //判断是否重复抢购
        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>()
                .eq("user_id", user.getId())
                .eq("goods_id", goodsId));
        if (seckillOrder != null){
            model.addAttribute("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
            return "secKillFail";
        }
        //可以抢购的情况(库存够，还没有抢购)
        Order order = orderService.seckill(user, goods);
        model.addAttribute("order", order);
        model.addAttribute("goods", goods);
        return "orderDetail";
    }

    @RequestMapping(value = "/doSeckill", method = RequestMethod.POST)
    @ResponseBody
    public RespBean doSecKill(Model model, User user, Long goodsId) {
        if (null == user) {
            //用户为空，返回错误
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        GoodsVo goods = goodsService.findGoodsByGoodsId(goodsId);
        //判断库存
        if (goods.getStockCount() < 1) {
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            //返回空库存错误
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        //判断是否重复抢购
        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>()
                .eq("user_id", user.getId())
                .eq("goods_id", goodsId));
        if (seckillOrder != null){
            model.addAttribute("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
            //返回重复抢购错误
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }
        //可以抢购的情况(库存够，还没有抢购)
        Order order = orderService.seckill(user, goods);

        return RespBean.success(order);
    }
}
