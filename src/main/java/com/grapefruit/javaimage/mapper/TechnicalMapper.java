/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.mapper;

import com.grapefruit.javaimage.entity.Technical;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-08-11 8:26 下午
 */
@Mapper
public interface TechnicalMapper {
    List<Technical> getTechnicals();
}
