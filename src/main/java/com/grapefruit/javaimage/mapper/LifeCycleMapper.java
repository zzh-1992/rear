/*
 *Copyright @2023 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.mapper;

import com.grapefruit.javaimage.entity.LifeCycle;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 功能
 *
 * @Author ZhangZhihuang
 * @Date 2023/1/14 18:57
 * @Version 1.0
 */
@Mapper
public interface LifeCycleMapper extends JpaRepository<LifeCycle, Integer> {
}
