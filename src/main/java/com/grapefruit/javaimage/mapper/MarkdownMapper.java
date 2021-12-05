/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.mapper;

import com.grapefruit.javaimage.entity.Markdown;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-11-06 11:48 上午
 */
@Mapper
public interface MarkdownMapper {

    /**
     * 依据id查询
     * @param id id
     * @return list
     */
    Markdown findById(String id);

    /**
     * 查询所有
     *
     * @return list
     */
    List<Markdown> selectAll();

    /**
     * 保存markdown
     *
     * @param markdown markdown
     */
    void save(Markdown markdown);
}
