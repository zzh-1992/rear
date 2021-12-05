/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.entity;

import lombok.Data;

/**
 * 角色
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-08-12 7:32 下午
 */
@Data
public class Role {
    String roleId;
    String permission;
    String role;

    public String[] getPermissionArray() {
        return this.permission.split(",");
    }
}
