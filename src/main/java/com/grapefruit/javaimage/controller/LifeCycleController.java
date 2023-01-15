/*
 *Copyright @2023 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.controller;

import com.grapefruit.javaimage.LifeCycleConstant;
import com.grapefruit.javaimage.entity.LifeCycle;
import com.grapefruit.javaimage.mapper.LifeCycleMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 功能
 *
 * @Author ZhangZhihuang
 * @Date 2023/1/14 09:29
 * @Version 1.0
 */
@RestController
@RequestMapping("/lifeCycle")
public class LifeCycleController {
    @Autowired
    LifeCycleMapper lifeCycleMapper;

    // primary / success / warning / danger / info

    @GetMapping("/selectOne")
    public LifeCycle getLifeCycle(@RequestParam(value = "id") Integer id) {
        LifeCycle lifeCycle = new LifeCycle();
        lifeCycle.setContent("test1");
        lifeCycle.setTimestamp(new Date());
        lifeCycle.setType(LifeCycleConstant.PRIMARY);
        lifeCycle.setStackStrace(error());
        return lifeCycle;
    }

    @GetMapping("/selectList")
    public List<LifeCycle> getLifeCycleList() {
        return lifeCycleMapper.findAll();
    }

    public static String error() {
        String message = "";
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            message = ExceptionUtils.getStackTrace(e);
        }
        return message;
    }
}
