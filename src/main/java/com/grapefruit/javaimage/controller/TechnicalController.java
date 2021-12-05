/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.controller;

import com.grapefruit.javaimage.entity.Technical;
import com.grapefruit.javaimage.http.rsp.AjaxResult;
import com.grapefruit.javaimage.service.TechnicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-08-11 8:21 下午
 */
@RestController
public class TechnicalController {

    @Autowired
    TechnicalService technicalService;

    @PostMapping("/getTechnicals")
    public AjaxResult getTechnicals(@RequestBody Object obj){
        List<Technical> list = technicalService.getTechnicals();

        AjaxResult success = AjaxResult.success();
        success.put("technicalList", list);
        return success;
    }
}
