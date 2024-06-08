package com.xxxx.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.seckill.pojo.Goods;
import com.xxxx.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author richard
 * @since 2023-11-23
 */
public interface IGoodsService extends IService<Goods> {

    /**
     * 功能描述:獲取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    /**
     * 功能描述:獲取商品詳情
     * @param goodsId
     * @return
     */
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
