package com.xxxx.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.seckill.pojo.Goods;
import com.xxxx.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author richard
 * @since 2023-11-23
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    /**
     * 功能描述:獲取商品列表
     *
     * @return
     */

     List<GoodsVo> findGoodsVo();

    /**
     * 功能描述:獲取商品詳情
     *
     * @return
     * @param goodsId
     */

    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
