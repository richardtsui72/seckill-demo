package com.xxxx.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.seckill.mapper.OrderMapper;
import com.xxxx.seckill.pojo.Order;
import com.xxxx.seckill.pojo.SeckillGoods;
import com.xxxx.seckill.pojo.SeckillOrder;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.service.IOrderService;
import com.xxxx.seckill.service.ISeckillGoodsService;
import com.xxxx.seckill.service.ISeckillOrderService;
import com.xxxx.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author richard
 * @since 2023-11-23
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 功能描述: 秒殺
     * @param user
     * @param goods
     * @return
     */
    @Transactional
    @Override
    public Order seckill(User user, GoodsVo goods) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //秒殺商品表減庫存
        SeckillGoods seckillGoods  = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id",goods.getId()));
        seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
//        seckillGoodsService.updateById(seckillGoods);
//       boolean seckillGoodsResult = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>().set("stock_count",seckillGoods.getStockCount()).eq("id",seckillGoods.getId()).gt("stock_count",0));
         boolean seckillGoodsResult = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>().setSql("stock_count= stock_count-1").eq("goods_id",goods.getId()).gt("stock_count",0));

//       if(!seckillGoodsResult){
//           return null;
//       }

//        if(seckillGoods.getStockCount()<1){
//            return null;
//        }


        if(seckillGoods.getStockCount()<1){
            //判斷是否還有庫存
            valueOperations.set("isStockEmpty:"+goods.getId(),"0");
            return null;
        }
       //生成訂單
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
        //生成秒殺訂單
        SeckillOrder SeckillOrder = new SeckillOrder();
        SeckillOrder.setUserId(user.getId());
        SeckillOrder.setOrderId(order.getId());
        SeckillOrder.setGoodsId(goods.getId());
        seckillOrderService.save(SeckillOrder);
        redisTemplate.opsForValue().set("order:" + user.getId() + ":" + goods.getId(),SeckillOrder);
        return order;
    }
}
