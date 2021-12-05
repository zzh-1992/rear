/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * user
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-07-30 8:53 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    /**
     * 权限列表
     */
    private String[] permissionArray;
    /**
     * 用户唯一标识
     */
    private String uid;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户角色
     */
    private int roleId;

    /**
     * 角色名称
     */
    private String role;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户创建时间
     */
    private String signUpTime;
}
