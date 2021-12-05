/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.config;

import com.alibaba.fastjson.JSONObject;
import com.grapefruit.utils.log.LogTools;
import org.slf4j.Logger;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-06-05 8:00 上午
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger log = LogTools.getDebugLogger();

    @ExceptionHandler
    public JSONObject handle(Exception ex) {
        // 创建响应体
        JSONObject j = new JSONObject();

        // 填充异常对象
        j.put("class", ex.getClass().getName());

        // 处理请求参数异常
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            List<ObjectError> errors = exception.getBindingResult().getAllErrors();
            StringBuilder sb = new StringBuilder();
            errors.forEach(e -> {
                sb.append(e.getDefaultMessage());
                sb.append(";");
            });

            j.put("code", 1);
            j.put("msg", sb.toString());
            j.put("content", "参数错误");
            log.error("参数错误:{}", sb);
            return j;
        }

        // 处理其他异常
        j.put("code", 2);
        j.put("msg", ex.getMessage());
        j.put("content", "error");
        return j;
    }

    /*@ExceptionHandler(value = { Exception.class })
    public ModelAndView nullPointerExceptionHandler(Exception e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("error", e.toString());
        mv.setViewName("error");
        return mv;
    }*/
}
