/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.http.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 登陆请求体
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-07-24 7:42 下午
 */
@Getter
@Setter
@ToString
public class LoginReq {
    @NotBlank
    @Length(max = 11, message = "手机长度不能超过11")
    String phone;

    @NotBlank
    @Length(max = 10, message = "密码长度不能超过10")
    String password;

    /**
     * 昵称
     */
    String nickName;

    /**
     * 短信验证码
     */
    String note;

    /**
     * 邮箱
     */
    String email;

    @NotBlank
    @Length(max = 10, message = "问题答案不能为空")
    String resoult;

    @NotBlank
    @Length(min = 10, message = "问题id不能为空")
    String questionId;
}
