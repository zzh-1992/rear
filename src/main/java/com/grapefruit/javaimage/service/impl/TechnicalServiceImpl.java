/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.service.impl;

import com.grapefruit.javaimage.entity.Technical;
import com.grapefruit.javaimage.mapper.TechnicalMapper;
import com.grapefruit.javaimage.service.TechnicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-08-11 8:26 下午
 */
@Service
public class TechnicalServiceImpl implements TechnicalService {
    @Autowired
    TechnicalMapper mapper;
    @Override
    public List<Technical> getTechnicals() {
        return mapper.getTechnicals();
    }
}
