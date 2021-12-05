/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.mapper;

import com.grapefruit.javaimage.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 权限_mapper
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-07-31 1:51 下午
 */
@Mapper
public interface RoleMapper {

    /**
     * 获取全量角色
     * @return list
     */
    List<Role> selectRoles();
}
