package com.xxxx.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.seckill.pojo.SeckillOrder;
import com.xxxx.seckill.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author richard
 * @since 2023-11-23
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    /**
     * 功能描述:獲取秒殺結果
     * @param user
     * @param goodsId:成功,   -1:秒殺失敗,  0:排隊中
     * @return
     */
    Long getResult(User user, Long goodsId);
}
