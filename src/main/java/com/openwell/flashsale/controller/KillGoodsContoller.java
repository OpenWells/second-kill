package com.openwell.flashsale.controller;

import com.openwell.flashsale.sys.controller.BaseController;
import com.openwell.flashsale.core.utils.response.HttpResponseBody;
import com.openwell.flashsale.mall.vo.KillGoodsSpecPriceDetailVo;
import com.openwell.flashsale.service.KillGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/killgoods")
public class KillGoodsContoller extends BaseController {

    @Autowired
    private KillGoodsService killGoodsService;


    @RequestMapping("/testLog")
    public String testLog(){
        log.info("testLog 接口");
        return "testLog";
    }

    /**
     * 通过数据库的方式秒杀库存实现
     * @param killId
     * @return
     */
    @PostMapping("killByDb")
    public HttpResponseBody killByDb(int killId){
        KillGoodsSpecPriceDetailVo killGoods = killGoodsService.detail(killId);
        if (killGoods.getBegainTime().getTime() > System.currentTimeMillis()){
            return HttpResponseBody.failResponse("抢购还未开始");
        }
        if (killGoods.getEndTime().getTime() < System.currentTimeMillis()){
            return HttpResponseBody.failResponse("抢购已结束");
        }
        if (!killGoodsService.secKillByDb(killId,getSessionUserId())){
            log.info("----抢购失败----");
            return HttpResponseBody.failResponse("抢购失败");
        }
        return HttpResponseBody.successResponse("ok",  killGoods);
    }


    /**
     * 通过redis ByIncrBy 实现秒杀
     * @param killId
     * @return
     */
    @PostMapping("killByIncrBy")
    public HttpResponseBody killByIncrBy(int killId){
        KillGoodsSpecPriceDetailVo killGoods = killGoodsService.detail(killId);
        if (killGoods.getBegainTime().getTime() > System.currentTimeMillis()){
            return HttpResponseBody.failResponse("抢购还未开始");
        }
        if (killGoods.getEndTime().getTime() < System.currentTimeMillis()){
            return HttpResponseBody.failResponse("抢购已结束");
        }
        if (!killGoodsService.killByIncrBy(killId,getSessionUserId())){
            log.info("----抢购失败----");
            return HttpResponseBody.failResponse("抢购失败");
        }
        return HttpResponseBody.successResponse("ok",  killGoods);
    }

    /**
     * 通过redis ByIncrBy 实现秒杀
     * @param killId
     * @return
     */
    @PostMapping("killByIncrBy")
    public HttpResponseBody killByLua(int killId){
        KillGoodsSpecPriceDetailVo killGoods = killGoodsService.detail(killId);
        if (killGoods.getBegainTime().getTime() > System.currentTimeMillis()){
            return HttpResponseBody.failResponse("抢购还未开始");
        }
        if (killGoods.getEndTime().getTime() < System.currentTimeMillis()){
            return HttpResponseBody.failResponse("抢购已结束");
        }
        if (!killGoodsService.killByLua(killId,getSessionUserId())){
            log.info("----抢购失败----");
            return HttpResponseBody.failResponse("抢购失败");
        }
        return HttpResponseBody.successResponse("ok",  killGoods);
    }








}
