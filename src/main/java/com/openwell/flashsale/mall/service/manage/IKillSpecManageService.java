package com.openwell.flashsale.mall.service.manage;

import com.openwell.flashsale.mall.model.KillGoodsPrice;
import com.openwell.flashsale.mall.vo.KillGoodsSpecPriceDetailVo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/product/mall/service/manage/IKillSpecManageService")
public interface IKillSpecManageService {

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    int delete(@RequestParam("id") Integer id);

    @RequestMapping(value = "/selectByPrimaryKey", method = RequestMethod.POST)
    KillGoodsPrice selectByPrimaryKey(@RequestParam("id") Integer id);

    @RequestMapping(value = "/updateSecKill", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    int updateSecKill(@RequestBody KillGoodsPrice record);

    @RequestMapping(value = "/detailById", method = RequestMethod.POST)
    KillGoodsSpecPriceDetailVo detailById(@RequestParam("id") Integer id);


}
