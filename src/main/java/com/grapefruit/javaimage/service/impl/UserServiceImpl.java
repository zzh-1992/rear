/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.service.impl;

import com.grapefruit.javaimage.entity.User;
import com.grapefruit.javaimage.mapper.UserMapper;
import com.grapefruit.javaimage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 用户操作
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-07-31 1:41 下午
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void save(String uid, String phone, String password, String nickName, String email) {
        String time = LocalDateTime.now(Clock.systemUTC()).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        String name = "Grape";
        // 先默认 2 teacher
        int roleId = 2;
        User user = User.builder()
                .uid(uid)
                .name(name)
                .roleId(roleId)
                .password(password)
                .email(email)
                .phone(phone)
                .nickName(nickName)
                .signUpTime(time)
                .build();
        System.out.println(user.toString());
        // TODO

        userMapper.save(user);
    }

    @Override
    public User selectUserByPhone(String phone) {
        return userMapper.selectUserByPhone(phone);
    }

    @Override
    public User selectUserByUid(String uid) {
        return userMapper.selectUserByUid(uid);
    }

    @Override
    public User selectUserByPhoneAndPassword(String phone, String password) {
        return userMapper.selectUserByPhoneAndPassword(phone, password);
    }
}
