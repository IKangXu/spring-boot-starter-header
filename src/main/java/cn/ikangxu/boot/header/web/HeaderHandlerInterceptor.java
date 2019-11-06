/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header.web;

import cn.ikangxu.boot.header.RootAttribute;
import cn.ikangxu.boot.header.RootHeader;
import cn.ikangxu.boot.header.util.CookieUtils;
import cn.ikangxu.boot.header.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @className HeaderHandlerInterceptor
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/10/18 14:02
 * @version v1.0
 */
@Slf4j
public class HeaderHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String[] keys = RootAttribute.list();
        if(null == keys || keys.length == 0) {
            return true;
        }

        for(int i= 0; i < keys.length; i++) {
            String key = keys[i];

            // 从header中获取token
            String val = request.getHeader(key);

            // 如果header中不存在token，则从COOKIE中获取
            if (StringUtils.isEmpty(val)) {
                val = CookieUtils.getCookie(key);
            }

            // 如果header中不存在token，则从参数中获取token
            if (StringUtils.isEmpty(val)) {
                val = request.getParameter(key);
            }
            if(!StringUtils.isEmpty(val)) {
                RootHeader.bind(key, val);
            }
        }

        return true;
    }
}
