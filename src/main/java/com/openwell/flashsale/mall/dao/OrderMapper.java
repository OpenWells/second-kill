package com.openwell.flashsale.mall.dao;

import org.apache.ibatis.annotations.Param;

public interface OrderMapper {

    Integer selectOrderNum(@Param("type") Integer type,
                           @Param("userId") String userId);
}
