/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan(basePackages = "com.grapefruit.javaimage.mapper") //或是使用注解@Mapper
@EnableTransactionManagement
//@RefreshScope
@SpringBootApplication(/*exclude= {DataSourceAutoConfiguration.class}*/)
public class ImgRear {
    public static void main(String[] args) {
        SpringApplication.run(ImgRear.class, args);
    }
}
