/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.service.impl;

import com.grapefruit.javaimage.entity.Markdown;
import com.grapefruit.javaimage.mapper.MarkdownMapper;
import com.grapefruit.javaimage.service.MarkdownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-11-06 11:45 上午
 */
@Service
public class MarkdownServiceImpl implements MarkdownService {
    @Autowired
    private MarkdownMapper mapper;

    @Override
    public Markdown findById(String id) {
        return mapper.findById(id);
    }

    @Override
    public List<Markdown> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public void save(Markdown markdown) {
        mapper.save(markdown);
    }

}
