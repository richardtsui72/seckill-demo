package com.xxxx.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.seckill.mapper.SeckillOrderMapper;
import com.xxxx.seckill.pojo.SeckillOrder;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.service.ISeckillOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author richard
 * @since 2023-11-23
 */
@Service
@Slf4j
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 功能描述:獲取秒殺結果
     * @param user
     * @param goodsId:成功,   -1:秒殺失敗,  0:排隊中
     * @return
     */
    @Override
    public Long getResult(User user, Long goodsId) {
        log.info("getResult_user: "+user.toString());
        log.info("getResult_goodsId: "+goodsId);
      SeckillOrder seckillOrder = seckillOrderMapper.selectOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
        log.info("getResult_seckillOrder.getOrderId(): "+seckillOrder.getOrderId());
        if(null != seckillOrder){
            return  seckillOrder.getOrderId();
        }else if(redisTemplate.hasKey("isStockEmpty"+goodsId)){
            return -1L;
        }else{
            return 0L;
        }

    }
}
