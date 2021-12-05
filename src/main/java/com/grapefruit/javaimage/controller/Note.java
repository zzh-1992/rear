/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.controller;

import com.grapefruit.javaimage.http.req.LoginReq;
import com.grapefruit.javaimage.http.rsp.AjaxResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

/**
 * 短信验证码处理
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-07-30 9:35 下午
 */
@RestController
@RequestMapping("/")
public class Note {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/note")
    public AjaxResult getNote(@RequestBody LoginReq req) {
        String phone = req.getPhone();
        AjaxResult ajax = new AjaxResult();
        if (StringUtils.isEmpty(phone)) {
            return AjaxResult.error("phone cannot be empty");
        }

        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        // 生成4位随机数
        for (int i = 0; i <= 3; i++) {
            int num = random.nextInt(9);
            sb.append(num);
        }
        String note = sb.toString();

        // 保存phone:note 4位随机数到redis
        redisTemplate.opsForValue().set(phone, note, 60, TimeUnit.SECONDS);

        // 返回服务验证码note
        ajax.put("note", note);
        ajax.put("code", "1");
        return ajax;
    }
}
