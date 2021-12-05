/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.service;

import com.google.code.kaptcha.Producer;
import com.grapefruit.javaimage.entity.Img;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 图片流处理
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-07-30 8:03 下午
 */
@Service
public class ImgService {
    private static final String imgdata = "data:image/gif;base64,";
    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public Img createImg() {
        // 生成验证码(String[] strings = capText.split("@"); 使用"@"分割字符串,得到算式和结果,结果存储用于后续交验)
        String capText = captchaProducerMath.createText();

        // 截取问题
        String capStr = capText.substring(0, capText.lastIndexOf("@"));

        // 截取结果 并存入redis(key:questionId,valuse:resoult)
        String resoult = capText.split("@")[1];

        String questionId = UUID.randomUUID().toString().replace("-", "");

        // 保存问题及时间(60秒)
        redisTemplate.opsForValue().set(questionId, resoult, 60, TimeUnit.SECONDS);
        BufferedImage image = captchaProducerMath.createImage(capStr);

        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            // TODO handle error
        }
        return new Img(resoult,imgdata + Base64.encode(os.toByteArray()),questionId);
    }
}
