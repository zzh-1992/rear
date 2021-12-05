/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Img {
    /**
     * 问题答案
     */
    String resolute;
    /**
     * 问题图片流
     */

    String img;
    /**
     * 问题ID
     */
    String qId;
}
