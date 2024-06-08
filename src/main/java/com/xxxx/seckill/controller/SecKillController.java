package com.xxxx.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.seckill.pojo.Order;
import com.xxxx.seckill.pojo.SeckillMessage;
import com.xxxx.seckill.pojo.SeckillOrder;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.rabbitmq.MQSender;
import com.xxxx.seckill.service.IGoodsService;
import com.xxxx.seckill.service.IOrderService;
import com.xxxx.seckill.service.ISeckillOrderService;
import com.xxxx.seckill.utlis.JsonUtil;
import com.xxxx.seckill.vo.GoodsVo;
import com.xxxx.seckill.vo.RespBean;
import com.xxxx.seckill.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seckill")
@Slf4j
@ResponseBody
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
    private MQSender mqSender;

    private Map<Long,Boolean> EmptyStockMap = new HashMap<>();
    /**
     * 功能描述:秒殺
     * window優化前QPS:638.7
     * Linux歐化前QPS:105.2
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/doSeckill2")
    public String doSeckill2(Model model, User user, Long goodsId){
        log.info("doSeckill2:");
        if(user==null){
            return "login";
        }
        model.addAttribute("user",user);
        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
        //判斷庫存
        if(goods.getStockCount()<1){
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return "secKillFail";
        }
        //判斷是否重複搶購
         SeckillOrder seckillOrder =seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().
                eq("user_id",user.getId()).eq("goods_id",goodsId));
            if(seckillOrder!=null){
                model.addAttribute("errmsg",RespBeanEnum.REPEATE_ERROR.getMessage());
                return  "secKillFail";
            }

          Order order = orderService.seckill(user,goods);
          model.addAttribute("order",order);
          model.addAttribute("goods",goods);
          return "orderDetail";
    }

    /**
     * 功能描述:秒殺
     * window優化前QPS:638.7
     * window優化後QPS:799.4
     * Linux歐化前QPS:105.2
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value ="/doSeckill",method = RequestMethod.POST)
    @ResponseBody
    public RespBean doSeckill(Model model, User user, Long goodsId){
        if(user==null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }

        ValueOperations valueOperations = redisTemplate.opsForValue();

        //判斷是否重複搶購
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:"+user.getId()+":"+goodsId);
        //String seckillOrderJson = (String) valueOperations.get("order:"+user.getId()+":"+goodsId);
        log.info("seckillOrder:  "+seckillOrder);
        if(seckillOrder != null){
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }
        //內存標記,減少Redis的訪問
        if(EmptyStockMap.get(goodsId)){
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        //預減庫存
        Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
        if(stock<0){
            EmptyStockMap.put(goodsId,true);
            valueOperations.increment("seckillGoods:" + goodsId);
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }

        SeckillMessage seckillMessage = new SeckillMessage(user,goodsId);
        mqSender.sendSeckillMessage(JsonUtil.object2JsonStr(seckillMessage));
        return RespBean.success(0);

        /*
        //判斷庫存
        if(goods.getStockCount()<1){
//            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        //判斷是否重複搶購
//        SeckillOrder seckillOrder =seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().
//                eq("user_id",user.getId()).eq("goods_id",goodsId));
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:"+user.getId()+":"+goodsId);
        if(seckillOrder!=null){
//            model.addAttribute("errmsg",RespBeanEnum.REPEATE_ERROR.getMessage());
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }

        Order order = orderService.seckill(user,goods);
        return RespBean.success(order);
        */


    }

    /**
     * 功能描述:獲取秒殺結果
     * @param user
     * @param goodsId:成功,   -1:秒殺失敗,  0:排隊中
     * @return
     */
    @RequestMapping(value = "/result",method = RequestMethod.GET)
    @ResponseBody
    public RespBean getResult(User user,Long goodsId){
        if(user==null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        log.info("result_user: "+user.toString());
        log.info("result_goodsId: "+goodsId);
        Long orderId = seckillOrderService.getResult(user,goodsId);
        log.info("result_orderId: "+orderId);
        return RespBean.success(orderId);
    }

    /**
     *
     * 系統初始化,把商品加載到Redis
     * Invoked by the containing {@code BeanFactory} after it has set all bean properties
     * and satisfied {@link //BeanFactoryAware}, {@code ApplicationContextAware} etc.
     * <p>This method allows the bean instance to perform validation of its overall
     * configuration and final initialization when all bean properties have been set.
     *
     * @throws Exception in the event of misconfiguration (such as failure to set an
     *                   essential property) or if initialization fails for any other reason
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list = goodsService.findGoodsVo();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(goodsVo -> {
                    redisTemplate.opsForValue().set("seckillGoods:" + goodsVo.getId(), goodsVo.getStockCount());
                    EmptyStockMap.put(goodsVo.getId(),false);
                }
                );

    }
}



