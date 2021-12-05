/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.mapper;

import com.grapefruit.javaimage.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户_mapper
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-07-31 1:51 下午
 */
@Mapper
public interface UserMapper {
    /**
     * 保存用户
     *
     * @param user user
     */
    void save(User user);

    /**
     * 使用phone查询用户
     *
     * @param phone phone
     * @return user
     */
    User selectUserByPhone(String phone);

    /**
     * 使用uid查询用户
     *
     * @param uid uid
     * @return user
     */
    User selectUserByUid(String uid);

    /**
     * 使用phone,password查询用户
     *
     * @param phone    phone
     * @param password password
     * @return user
     */
    User selectUserByPhoneAndPassword(String phone, String password);


    List<User> getUserList();

    int deleteUser(String uid);
}
