/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.http.req;

import lombok.Data;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-08-12 10:38 上午
 */
@Data
public class BaseReq {
    String token;
    String phone;
    String uid;
}
