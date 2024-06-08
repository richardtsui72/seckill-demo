package com.xxxx.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.seckill.pojo.Order;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.vo.GoodsVo;

/**
 * 功能描述: 秒殺
 * <p>
 *  服务类
 * </p>
 *
 * @author richard
 * @since 2023-11-23
 */

public interface IOrderService extends IService<Order> {

    Order seckill(User user, GoodsVo goods);




}
