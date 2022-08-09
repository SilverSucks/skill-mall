package com.example.skillmall.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.skillmall.pojo.Order;
import com.example.skillmall.pojo.SeckillMessage;
import com.example.skillmall.pojo.SeckillOrder;
import com.example.skillmall.pojo.User;
import com.example.skillmall.rabbitmq.MQSender;
import com.example.skillmall.rabbitmq.SeckillMQSender;
import com.example.skillmall.service.IGoodsService;
import com.example.skillmall.service.IOrderService;
import com.example.skillmall.service.ISeckillOrderService;
import com.example.skillmall.utils.JsonUtil;
import com.example.skillmall.vo.GoodsVo;
import com.example.skillmall.vo.RespBean;
import com.example.skillmall.vo.RespBeanEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version v1.0
 * @Description TODO
 * @Date 2021-11-24 8:58
 */

@Controller
@RequestMapping("/seckill")
public class SecKillController implements InitializingBean {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SeckillMQSender seckillMQSender;
    private Map<Long, Boolean> EmptyStockMap = new HashMap<>();

//    @RequestMapping("/doSeckill2")
//    public String doSecKill2(Model model, User user, Long goodsId) {
//        if (null == user) {
//            //用户为空，返回登录页面
//            return "login";
//        }
//        model.addAttribute("user", user);
//        GoodsVo goods = goodsService.findGoodsByGoodsId(goodsId);
//        //判断库存
//        if (goods.getStockCount() < 1) {
//            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
//            return "secKillFail";
//        }
//        //判断是否重复抢购
//        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>()
//                .eq("user_id", user.getId())
//                .eq("goods_id", goodsId));
//        if (seckillOrder != null){
//            model.addAttribute("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
//            return "secKillFail";
//        }
//        //可以抢购的情况(库存够，还没有抢购)
//        Order order = orderService.seckill(user, goods);
//        model.addAttribute("order", order);
//        model.addAttribute("goods", goods);
//        return "orderDetail";
//    }

    @RequestMapping(value = "/doSeckill", method = RequestMethod.POST)
    @ResponseBody
    public RespBean doSecKill(Model model, User user, Long goodsId) {
        if (null == user) {
            //用户为空，返回错误
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        GoodsVo goods = goodsService.findGoodsByGoodsId(goodsId);
        /*
        //判断库存
        if (goods.getStockCount() < 1) {
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            //返回空库存错误
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        //判断是否重复抢购
//        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>()
//                .eq("user_id", user.getId())
//                .eq("goods_id", goodsId));
        //把抢购订单加到redis中,从redis中获取
        SeckillOrder seckillOrder =
                (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);

        if (seckillOrder != null){
            model.addAttribute("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
            //返回重复抢购错误
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }
        //可以抢购的情况(库存够，还没有抢购)
        Order order = orderService.seckill(user, goods);

        return RespBean.success(order);
         */

        ValueOperations valueOperations = redisTemplate.opsForValue();
        //判断是否重复抢购
        String seckillOrderJson = (String) valueOperations.get("order:" + user.getId() + ":" + goodsId);
        if (!StringUtils.isEmpty(seckillOrderJson)){
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }
        //内存标记，较少Redis访问
        if (EmptyStockMap.get(goodsId)){
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        //预减库存, decrement(),如果value是数值类型的话，每调用一次会减1，而且是原子性的
        Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
        if (stock < 0){
            EmptyStockMap.put(goodsId, true);
            valueOperations.increment("seckillGoods:"+goodsId);
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        // 请求入队，立即返回排队中
        SeckillMessage message = new SeckillMessage(user, goodsId);
        //对象转JSON字符串
        seckillMQSender.sendsecKillMessage(JsonUtil.object2JsonStr(message));
        return RespBean.success(0);
    }

    /**
     * 功能描述；获取秒杀结果
     * @param user
     * @param goodsId
     * @return orderId，如果有orderId，表示秒杀成功；如果是-1，秒杀失败；0表示排队中
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public RespBean getResult(User user, Long goodsId){
        if (user == null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        Long orderId = seckillOrderService.getResult(user, goodsId);
        return RespBean.success(orderId);
    }

    /**
     * 系统初始化，把商品库存数量加载到Redis
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list = goodsService.findGoodsVo();
        if (CollectionUtils.isEmpty(list)){
            return;
        }
        list.forEach(goodsVo -> {
            redisTemplate.opsForValue().set("seckillGoods:"+goodsVo.getId(), goodsVo.getStockCount());
            EmptyStockMap.put(goodsVo.getId(), false);
        });

    }
}















