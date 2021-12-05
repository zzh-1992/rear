/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.service;

import com.grapefruit.javaimage.entity.User;

import java.util.List;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-08-12 10:34 上午
 */
public interface UserDetailService {
    User getUserDetail(String uid);

    List<User> getUserList();

    int deleteUser(String uid);
}
