/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header.web;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @className HeaderHandlerInterceptorConfiguration
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/10/18 14:34
 * @version v1.0
 */
public class HeaderHandlerInterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HeaderHandlerInterceptor()).addPathPatterns("/**");
    }
}