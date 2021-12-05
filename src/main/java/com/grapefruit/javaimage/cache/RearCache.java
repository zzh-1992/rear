/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.cache;

import com.grapefruit.javaimage.entity.Role;
import com.grapefruit.javaimage.mapper.RoleMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-08-12 7:24 下午
 */
@Service
public class RearCache implements ApplicationContextAware {

    ApplicationContext context;

    @Autowired
    RoleMapper mapper;

    private ConcurrentHashMap<String, String[]> roleMap;

    /**
     * 从数据库加载权限到本地缓存
     */
    private void loadRole() {
        List<Role> roles = new ArrayList<>();
        try {
            roles = mapper.selectRoles();
        } catch (Exception e) {
            e.printStackTrace();
        }
        roleMap = new ConcurrentHashMap<>(roles.stream().collect(Collectors.toMap(Role::getRoleId,
                Role::getPermissionArray)));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        loadRole();
    }

    /**
     * 对外暴露获取权限到方法
     *
     * @param key key
     * @return 权限数组
     */
    public String[] getRoleMap(String key) {
        return roleMap.get(key);
    }
}
