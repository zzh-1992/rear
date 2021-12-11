/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-07-31 1:38 下午
 */
@RestController
@RequestMapping("/")
public class Health {
    @GetMapping(value = "/")
    public String health() {
        return "RearService is running ======>";
    }
}
