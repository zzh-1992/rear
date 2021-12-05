/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.controller;

import com.grapefruit.javaimage.entity.Img;
import com.grapefruit.javaimage.http.rsp.AjaxResult;
import com.grapefruit.javaimage.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取图片验证码
 *
 * @author grapefruit
 */
@RestController
public class CaptchaController {

    @Autowired
    ImgService imgService;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public AjaxResult getCode() {
        AjaxResult ajax = AjaxResult.success();

        Img img = imgService.createImg();

        ajax.put("img", img.getImg());
        ajax.put("qId", img.getQId());

        return ajax;
    }
}
