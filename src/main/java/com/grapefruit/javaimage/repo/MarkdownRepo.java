/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.repo;

import com.grapefruit.javaimage.entity.Markdown;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-12-04 11:40 下午
 */
public interface MarkdownRepo extends JpaRepository<Markdown,Integer> {
}
