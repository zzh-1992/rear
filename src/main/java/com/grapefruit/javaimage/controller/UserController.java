/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.controller;

import com.alibaba.fastjson.JSON;
import com.grapefruit.javaimage.entity.User;
import com.grapefruit.javaimage.http.req.BaseReq;
import com.grapefruit.javaimage.http.rsp.AjaxResult;
import com.grapefruit.javaimage.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户相关接口
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-08-12 10:31 上午
 */
@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    UserDetailService userDetailService;

    @RequestMapping("/getUserDetail")
    public AjaxResult getUserDetail(@RequestBody BaseReq req) {
        String uid = req.getUid();

        // TODO handle token
        String token = req.getToken();

        AjaxResult success = AjaxResult.success();

        User user = userDetailService.getUserDetail(uid);
        success.put("user", user);
        return success;
    }

    @RequestMapping("/getUserList")
    public AjaxResult getUserList(@RequestBody BaseReq req) {
        //String uid = req.getUid();

        // TODO handle token(查询所有用户是"超级管理员才有的权限",需要对toke、use做处理)
        //String token = req.getToken();
        AjaxResult success = AjaxResult.success();
        List<Map> list = new ArrayList<>();
        try {
            List<User> userList = userDetailService.getUserList();
            userList.forEach(user -> {
                String string = JSON.toJSONString(user);
                Map map = JSON.parseObject(string, Map.class);
                list.add(map);
            });

            success.put("userList", userList);
            success.put("list", list);
        } catch (Exception e) {
            success = AjaxResult.error("操作失败");
        }
        return success;
    }

    @PostMapping("/deleteUser")
    public AjaxResult deleteUser(@RequestBody BaseReq req) {
        // TODO 处理登陆及权限
        String token = req.getToken();

        String uid = req.getUid();
        AjaxResult res = AjaxResult.success("删除成功");
        if (userDetailService.deleteUser(uid) != 1) {
            res = AjaxResult.error("删除失败,uid:" + uid);
        }
        return res;
    }
}
