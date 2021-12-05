/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.service;

import com.grapefruit.javaimage.entity.Markdown;

import java.util.List;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-11-06 11:44 上午
 */
public interface MarkdownService {

    Markdown findById(String id);

    List<Markdown> selectAll();

    void save(Markdown markdown);
}
