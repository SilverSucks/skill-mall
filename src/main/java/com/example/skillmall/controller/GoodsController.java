package com.example.skillmall.controller;

import com.example.skillmall.pojo.User;
import com.example.skillmall.service.IGoodsService;
import com.example.skillmall.service.IUserService;
import com.example.skillmall.vo.DetailVo;
import com.example.skillmall.vo.GoodsVo;
import com.example.skillmall.vo.RespBean;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @version v1.0
 * @Description 跳转到商品详情页
 * @Date 2021-11-21 18:31
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;


//    @RequestMapping("/toList")
//    public String toList(HttpServletRequest request, HttpServletResponse response,
//                         Model model, @CookieValue("userTicket") String ticket){
//        //通过session获取用户，判断用户是否存在，如果用户不存在，将其跳回到登录页面
//        if (!StringUtils.hasLength(ticket)){
//            return "login";
//        }
////        User user = (User) session.getAttribute(ticket);
//        User user = userService.getUserByCookie(ticket, request, response);
//        if (null == user){
//            return "login";
//        }
//        //如果都没有问题，把user对象传到前端页面
//        model.addAttribute("user", user);
//        return "goodsList";
//    }

    @RequestMapping(value = "/toList", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toList(Model model, User user,
                         HttpServletRequest request,
                         HttpServletResponse response) {

        //Redis中获取页面，如果不为空，直接返回页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsList");
        if (!StringUtils.isEmpty(html)){
            return html;
        }
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());
//        return "goodsList";
        //如果为空，手动渲染，存入Redis并返回
        WebContext context = new WebContext(request, response,
                request.getServletContext(),
                request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", context);
        if (!StringUtils.isEmpty(html)){
            valueOperations.set("goodsList", html, 60, TimeUnit.SECONDS);
        }
        return html;
    }

    /**
     * 跳转商品详情页
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
//    @RequestMapping(value = "/toDetail/{goodsId}", produces = "text/html;charset=utf-8")
//    @ResponseBody
//    public String toDetail(Model model, User user, @PathVariable Long goodsId,
//                           HttpServletRequest request, HttpServletResponse response) {
//        ValueOperations valueOperations = redisTemplate.opsForValue();
//        //Redis中获取页面，如果不为空，直接返回页面
//        String html = (String) valueOperations.get("goodsDetail:" + goodsId);
//        if (!StringUtils.isEmpty(html)) {
//            return html;
//        }
//        model.addAttribute("user", user);
//        GoodsVo goodsVo = goodsService.findGoodsByGoodsId(goodsId);
//        //获取开始时间
//        Date startDate = goodsVo.getStartDate();
//        //获取结束时间
//        Date endDate = goodsVo.getEndDate();
//        //当前时间
//        Date nowDate = new Date();
//        //秒杀状态
//        int secKillStatus = 0;
//        //秒杀倒计时
//        int remainSeconds = 0;
//        //当前时间在开始时间之前，秒杀还未开始
//        if (nowDate.before(startDate)) {
//            //秒杀开始时间-当前时间
//            remainSeconds = ((int) ((startDate.getTime() - nowDate.getTime()) / 1000));
//        } else if (nowDate.after(endDate)) {
//            //秒杀已结束
//            secKillStatus = 2;
//            remainSeconds = -1;
//        } else { //秒杀中
//            secKillStatus = 1;
//            remainSeconds = 0;
//        }
//        //把秒杀倒计时传向后端
//        model.addAttribute("remainSeconds", remainSeconds);
//        model.addAttribute("secKillStatus", secKillStatus);
//        model.addAttribute("goods", goodsVo);
//        WebContext context = new WebContext(request, response, request.getServletContext(),
//                request.getLocale(), model.asMap());
//        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", context);
//        if (!StringUtils.isEmpty(html)){
//            valueOperations.set("goodsDetail"+goodsId, html, 60, TimeUnit.SECONDS);
//        }
//        return html;
////        return "goodsDetail";
//    }

    /**
     *
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/toDetail/{goodsId}")
    @ResponseBody
    public RespBean toDetail2(User user, @PathVariable Long goodsId) {

        GoodsVo goods = goodsService.findGoodsByGoodsId(goodsId);
        //获取开始时间
        Date startDate = goods.getStartDate();
        //获取结束时间
        Date endDate = goods.getEndDate();
        //当前时间
        Date nowDate = new Date();
        //秒杀状态
        int secKillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        //当前时间在开始时间之前，秒杀还未开始
        if (nowDate.before(startDate)) {
            //秒杀开始时间-当前时间
            remainSeconds = ((int) ((startDate.getTime() - nowDate.getTime()) / 1000));
        } else if (nowDate.after(endDate)) {
            //秒杀已结束
            secKillStatus = 2;
            remainSeconds = -1;
        } else { //秒杀中
            secKillStatus = 1;
            remainSeconds = 0;
        }
        DetailVo detailVo = new DetailVo();
        detailVo.setGoodsVo(goods);
        detailVo.setUser(user);
        detailVo.setRemainSeconds(remainSeconds);
        detailVo.setSecKillStatus(secKillStatus);
        return RespBean.success(detailVo);
    }
}
