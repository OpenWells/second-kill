package com.openwell.flashsale.mall.service.manage.impl;

import com.openwell.flashsale.mall.constant.KillConstants;
import com.openwell.flashsale.mall.dao.KillGoodsPriceMapper;
import com.openwell.flashsale.mall.model.KillGoodsPrice;
import com.openwell.flashsale.mall.service.manage.IKillSpecManageService;
import com.openwell.flashsale.mall.vo.KillGoodsSpecPriceDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class KillGoodsManageServiceImpl implements IKillSpecManageService {


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private KillGoodsPriceMapper killGoodsPriceMapper;

    @Override
    public int delete(Integer id) {
        //清缓存
        stringRedisTemplate.delete(KillConstants.KILLGOODS_LIST);
        stringRedisTemplate.delete(KillConstants.KILL_GOOD_COUNT + id);
        return killGoodsPriceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public KillGoodsPrice selectByPrimaryKey(Integer id) {
        return killGoodsPriceMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateSecKill(KillGoodsPrice record) {
        int i = killGoodsPriceMapper.updateSecKill(record);
        return i;
    }

    @Override
    public KillGoodsSpecPriceDetailVo detailById(Integer id) {
        return killGoodsPriceMapper.detail(id);
    }




}
