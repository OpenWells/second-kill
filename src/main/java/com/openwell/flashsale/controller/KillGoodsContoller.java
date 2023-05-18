package com.openwell.flashsale.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/killgoods")
public class KillGoodsContoller {


    @RequestMapping("/testLog")
    public String testLog(){
        log.info("testLog 接口");
        return "testLog";
    }



}
