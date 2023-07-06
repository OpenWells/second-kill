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
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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

    public static final String STOCK_LUA ;

    public static String STOCK_LUA_1 = "";

    public static String STOCK_LUA_INCR = "";

    public static final long UNINITIALIZED_STOCK = -3L;
    static {
        /**
         *
         * @desc 扣减库存Lua脚本
         * 库存（stock）-1：表示不限库存
         * 库存（stock）0：表示没有库存
         * 库存（stock）大于0：表示剩余库存
         *
         * @params 库存key
         * @return
         * 		-3:库存未初始化
         * 		-2:库存不足
         * 		-1:不限库存
         * 		大于等于0:剩余库存（扣减之后剩余的库存）
         * 	    redis缓存的库存(value)是-1表示不限库存，直接返回1
         */
        StringBuilder sb = new StringBuilder();
        sb.append("if (redis.call('exists', KEYS[1]) == 1) then");
        sb.append("    local stock = tonumber(redis.call('get', KEYS[1]));");
        sb.append("    local num = tonumber(ARGV[1]);");
        sb.append("    if (stock == -1) then");
        sb.append("        return -1;");
        sb.append("    end;");
        sb.append("    if (stock >= num) then");
        sb.append("        return redis.call('incrby', KEYS[1], 0 - num);");
        sb.append("    end;");
        sb.append("    return -2;");
        sb.append("end;");
        sb.append("return -3;");
        STOCK_LUA = sb.toString();

        StringBuilder sb1 = new StringBuilder();
        sb1.append("if (redis.call('exists', KEYS[1]) == 1) then");
        sb1.append("    local stock = tonumber(redis.call('get', KEYS[1]));");
        sb1.append("    local num = tonumber(ARGV[1]);");
        sb1.append("    if (stock >= num) then");
        sb1.append("        return redis.call('incrby', KEYS[1], 0 - num);");
        sb1.append("    end;");
        sb1.append("    return -2;");
        sb1.append("end;");
        sb1.append("return -3;");
        STOCK_LUA_1 = sb1.toString();

        StringBuilder sb2 = new StringBuilder();
        sb2.append("if (redis.call('exists', KEYS[1]) == 1) then");
        sb2.append("    local stock = tonumber(redis.call('get', KEYS[1]));");
        sb2.append("    local num = tonumber(ARGV[1]);");
        sb2.append("    if (stock >= 0) then");
        sb2.append("        return redis.call('incrby', KEYS[1], num);");
        sb2.append("    end;");
        sb2.append("    return -2;");
        sb2.append("end;");
        sb2.append("return -3;");
        STOCK_LUA_INCR = sb2.toString();
    }

    public static void main(String[] args) {
        System.out.println(STOCK_LUA);
    }


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
            log.info(killGoodsPriceOb.toString());
            return JSONObject.parseObject(killGoodsPriceOb.toString(), KillGoodsSpecPriceDetailVo.class);
        }
        //程序要健壮一些防止并发用户瞬间穿透
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

    /**
     * 通过incrby 减库存
     * 会出现超卖 而且 效率很低
     *
     * @param killId
     * @param userId
     * @return
     */
    public boolean killByIncrBy(int killId, String userId) {
        //判断用户是否秒杀
//        Boolean user =redisTemplate.opsForSet().isMember(KillConstants.KILLED_GOOD_USER + killId, userId);
//        if(user){
//            logger.info("--------useId+"+ userId +" has kill:------------");
//            return false;
//        }
        // 库存减1
        String killGoodCount = KillConstants.KILL_GOOD_COUNT + killId;

        if (redisTemplate.opsForValue().increment(killGoodCount, -1) < 0) {
            logger.info("--------stock :余量不足--------");
            return false;
        }


        // 把用户缓存到redis
        redisTemplate.opsForSet().add(KillConstants.KILLED_GOOD_USER + killId, userId);
        // 完成秒杀

        return true;
    }


    public boolean killByLua(int killId, String userId) {

//        判断用户是否秒杀
//        Boolean user =redisTemplate.opsForSet().isMember(KillConstants.KILLED_GOOD_USER + killId, userId);
//        if(user){
//            logger.info("--------useId+"+ userId +" has kill:------------");
//            return false;
//        }
        // 库存的key
        String killGoodCount = KillConstants.KILL_GOOD_COUNT + killId;

        // lua 脚本获取 是否还有库存 如有有返回1 没有返回 0
        long sto = stock(killGoodCount, 1, STOCK_LUA);


        return true;
    }

    /**
     * 扣库存
     *
     * @param key    库存key
     * @param num    扣减库存数量
     * @param script 扣减库存数量
     * @return 扣减之后剩余的库存【-3:库存未初始化; -2:库存不足; -1:不限库存; 大于等于0:扣减库存之后的剩余库存】
     */

    public long stock(String key, int num, String script) {

        List<String> keys = new ArrayList<>();
        keys.add(key);
        List<String> args = new ArrayList<>();
        args.add(Integer.toString(num));

        long result = (long) redisTemplate.execute(new RedisCallback() {
           @Override
           public Object doInRedis(RedisConnection connection) throws DataAccessException {
               Object nativeConnection = connection.getNativeConnection();
               if (nativeConnection instanceof Jedis) {
                   return (Long)  ((Jedis)nativeConnection).eval(script,keys,args);
               }

               return  UNINITIALIZED_STOCK ;


           }
       }


        );

        return result;


    }


}
