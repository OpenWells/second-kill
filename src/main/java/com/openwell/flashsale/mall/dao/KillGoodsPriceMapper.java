package com.openwell.flashsale.mall.dao;

import com.openwell.flashsale.mall.model.KillGoodsPrice;
import com.openwell.flashsale.mall.vo.KillGoodsSpecPriceDetailVo;

public interface KillGoodsPriceMapper {

    int deleteByPrimaryKey(Integer id);

    KillGoodsPrice selectByPrimaryKey(Integer id);

    int updateSecKill(KillGoodsPrice record);

    KillGoodsSpecPriceDetailVo detail(Integer id);

}
