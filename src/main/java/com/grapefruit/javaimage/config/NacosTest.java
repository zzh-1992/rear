/*
 *Copyright @2022 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * nacos配置拉取测试
 *
 * @Author ZhangZhihuang
 * @Date 2022/11/19 07:35
 * @Version 1.0
 */
@Service
public class NacosTest {

    @NacosValue(value = "${name}")
    private String name;

    @PostConstruct
    public void init(){
        System.out.println();
    }
}
