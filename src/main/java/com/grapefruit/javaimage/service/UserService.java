/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.service;

import com.grapefruit.javaimage.entity.User;

/**
 * 用户_service
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-07-31 1:38 下午
 */
public interface UserService {

    void save(String uid,String phone, String password, String nickName, String email);

    User selectUserByPhone(String phone);

    User selectUserByUid(String uid);

    User selectUserByPhoneAndPassword(String phone,String password);
}
