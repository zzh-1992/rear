/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.controller;

import com.grapefruit.javaimage.http.req.LoginReq;
import com.grapefruit.javaimage.http.rsp.AjaxResult;
import com.grapefruit.javaimage.service.UserService;
import com.grapefruit.utils.security.TokenUtils;
import com.grapefruit.utils.string.LocalStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 用户注册处理
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-07-31 1:38 下午
 */
@RestController
@RequestMapping("/")
public class Signup {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/signup")
    public AjaxResult signup(@RequestBody @Validated LoginReq req) throws NoSuchAlgorithmException, IOException,
            InvalidKeySpecException {

        String phone = req.getPhone();
        String password = req.getPassword();
        String nickName = req.getNickName();
        String email = req.getEmail();
        String resoult = req.getResoult();
        String questionId = req.getQuestionId();

        // 从redis获取问题答案
        String cachingResoult = redisTemplate.opsForValue().get(questionId);
        if (cachingResoult == null || !cachingResoult.equals(resoult)) {
            // TODO 验证码已经失效
            return AjaxResult.error("答案错误,请尝试刷新图片");
        }
        // save user
        // TODO handle encrypt password and error
        String uid = LocalStringUtils.getUUID();
        userService.save(uid, phone, password, nickName, email);

        // 使用账户及密码生成token  userName password uuid
        String token = TokenUtils.generateTokenWithRSA512(phone, password, 30 * 60L);
        // 返回token
        AjaxResult success = AjaxResult.success();
        success.put("uid", uid);
        success.put("token", token);
        // 异常情况暂时不处理
        return success;
    }
}
