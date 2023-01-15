/*
 *Copyright @2023 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 功能
 *
 * @Author ZhangZhihuang
 * @Date 2023/1/14 09:30
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_life_cycle", schema = "grapefruit")
@Entity
public class LifeCycle {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    Integer id;

    String content;

    /**
     * primary / success / warning / danger / info
     */
    String type;

    Boolean hollow;

    @Column(name = "stack_strace")
    String stackStrace;
    @Column(name = "time_stamp")
    Date timestamp;
}
