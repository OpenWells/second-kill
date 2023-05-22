package com.openwell.flashsale.mall.service.manage.impl;

import com.openwell.flashsale.mall.dao.OrderMapper;
import com.openwell.flashsale.mall.service.manage.IKillOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class OrderServiceImpl implements IKillOrderService {

    @Resource
    private OrderMapper orderMapper;
    @Override
    public Integer queryCountByUserId(String userId) {
        return orderMapper.selectOrderNum(1,userId);
    }
}
