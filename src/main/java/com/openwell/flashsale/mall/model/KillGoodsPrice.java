package com.openwell.flashsale.mall.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class KillGoodsPrice implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer specGoodsId;

    private Integer status;

    private BigDecimal price;

    private Integer killCount;

    private Integer freezedKillCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date begainTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}