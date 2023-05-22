package com.openwell.flashsale.service;

import com.alibaba.fastjson.JSONObject;
import com.openwell.flashsale.mall.constant.KillConstants;
import com.openwell.flashsale.mall.model.KillGoodsPrice;
import com.openwell.flashsale.mall.service.manage.IKillOrderService;
import com.openwell.flashsale.mall.service.manage.IKillSpecManageService;
import com.openwell.flashsale.mall.vo.KillGoodsSpecPriceDetailVo;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
@Slf4j
@Service
public class KillGoodsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private IKillSpecManageService iKillSpecManageService;

    @Autowired
    private IKillSpecManageService killSpecManageService;

    @Autowired
    private IKillOrderService orderService;


    public KillGoodsSpecPriceDetailVo detail(Integer id) {
        KillGoodsSpecPriceDetailVo killGoodsPrice = null;
        //1、先从缓存里面查询数据
        String killGoodsDetail = KillConstants.KILLGOOD_DETAIL + id;

        //1、从本地缓存里面查询数据
        Cache killgoodsCache = cacheManager.getCache("killgoodDetail");
        if (null != killgoodsCache.get(killGoodsDetail)) {
            log.info(Thread.currentThread().getName() + "--------ehcache缓存里面的到数据-------");
            killGoodsPrice = (KillGoodsSpecPriceDetailVo) killgoodsCache.get(killGoodsDetail).getObjectValue();
            return killGoodsPrice;
        }

        //2、本地缓存里面没有从redis里面拿
        Object killGoodsPriceOb = redisTemplate.opsForValue().get(killGoodsDetail);
        if (null != killGoodsPriceOb) {
            log.info(Thread.currentThread().getName() + "---redis缓存中得到的数据---------");
            return JSONObject.parseObject(killGoodsPriceOb.toString(), KillGoodsSpecPriceDetailVo.class);
        }
        //程序要健壮一些
        synchronized (iKillSpecManageService) {
            //1、从本地缓存里面查询数据
            if (null != killgoodsCache.get(killGoodsDetail)) {
                log.info(Thread.currentThread().getName() + "--------ehcache缓存里面的到数据-------");
                killGoodsPrice = (KillGoodsSpecPriceDetailVo) killgoodsCache.get(killGoodsDetail).getObjectValue();
                return killGoodsPrice;

            }

            //1、从缓存里面拿
            killGoodsPriceOb = redisTemplate.opsForValue().get(killGoodsDetail);
            if (null != killGoodsPriceOb) {
                log.info(Thread.currentThread().getName() + "---redis缓存中得到的数据---------");
                return JSONObject.parseObject(killGoodsPriceOb.toString(), KillGoodsSpecPriceDetailVo.class);
            }
            //2、去数据库里面查询数据
            killGoodsPrice = iKillSpecManageService.detailById(id);
            if (null != killGoodsPrice) {
                killgoodsCache.putIfAbsent(new Element(killGoodsDetail, killGoodsPrice));
                redisTemplate.opsForValue().set(killGoodsDetail, killGoodsPrice, 2, TimeUnit.DAYS);
            } else {
                //防止缓存穿透  缓存时间一定要短 ，空数据没有必要占用redis内存
                redisTemplate.opsForValue().set(killGoodsDetail, "null", 5, TimeUnit.MINUTES);
            }
        }
        return killGoodsPrice;
    }

    /**
     * 基于数据库的秒杀实现
     *
     * @param
     * @return
     * @throws Exception
     * @author Jack
     * @date 2020/8/5
     * @version
     */
    public boolean secKillByDb(int killId, String userId) {

        //1、先判断有没有库存，没有库存就直接秒杀结束
        KillGoodsPrice kgp = killSpecManageService.selectByPrimaryKey(killId);
        if (kgp.getKillCount() <= 0) {
            logger.info("--------Insufficient stock:------------");
            return false;
        }

        //2、先判断该用户是否已经秒杀
        Integer count = orderService.queryCountByUserId(userId);
        if (/*orders != null && orders.size() > 0*/false) {
            logger.info("--------userId:" + userId + "--has secKilled");
            return false;
        }

        KillGoodsPrice killGoodsPrice = new KillGoodsPrice();
        killGoodsPrice.setKillCount(1);
        killGoodsPrice.setId(killId);
        int i = killSpecManageService.updateSecKill(killGoodsPrice);

        //返回为0，秒杀完了
        if (i == 0) {
            logger.info("--------Insufficient stock:------------");
            return false;
        }

        //秒杀成功，缓存秒杀用户和商品
        redisTemplate.opsForSet().add(KillConstants.KILLED_GOOD_USER + killId, userId);
        return true;
    }
}
