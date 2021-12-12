/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-11-06 11:28 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_markdown",schema = "grapefruit")
@Entity
public class Markdown {
    /**
     * '主键
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    Integer id;

    /**
     * 标题
     */
    @Column(name = "title")
    String title;

    /**
     * 内容
     */
    @Column(name = "content")
    String content;

    /**
     * 修改人
     */
    @Column(name = "modifier")
    String modifier;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    String modifyTime;

    /**
     * 标签/分类
     */
    @Column(name = "tags")
    String tags;

    @Transient
    List<String> tagArray;
}
