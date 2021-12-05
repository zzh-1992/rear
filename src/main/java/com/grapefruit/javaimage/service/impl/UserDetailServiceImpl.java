/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.service.impl;

import com.grapefruit.javaimage.cache.RearCache;
import com.grapefruit.javaimage.entity.User;
import com.grapefruit.javaimage.mapper.UserMapper;
import com.grapefruit.javaimage.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-08-12 10:35 上午
 */
@Service
public class UserDetailServiceImpl implements UserDetailService {
    @Autowired
    UserMapper mapper;

    @Autowired
    RearCache cache;

    @Override
    public User getUserDetail(String uid) {
        User user = mapper.selectUserByUid(uid);
        // 从本地缓存获取用户对应到权限
        user.setPermissionArray(cache.getRoleMap(String.valueOf(user.getRoleId())));
        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> list = mapper.getUserList();
        return list;
    }

    @Override
    public int deleteUser(String uid) {
        return mapper.deleteUser(uid);
    }
}
