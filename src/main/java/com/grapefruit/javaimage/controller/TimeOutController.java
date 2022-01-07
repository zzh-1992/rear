/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2022-01-07 8:48 下午
 */
@RestController
public class TimeOutController {

    @GetMapping("/timeOut")
    public String timeOut(){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "timeOut success";
    }
}
