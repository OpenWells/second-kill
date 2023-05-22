package com.openwell.flashsale.mall.service.manage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/kill/order/service/IKillOrderService")
public interface IKillOrderService
{

    @RequestMapping(value = "/queryCountByUserId", method = RequestMethod.POST)
    Integer queryCountByUserId(@RequestParam("userId") String userId);
}
