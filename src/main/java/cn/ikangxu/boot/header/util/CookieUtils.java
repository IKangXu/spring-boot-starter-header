/*
 * Engine3D Technologies Co., Ltd. Copyright 2019, All rights reserved
 */

package cn.ikangxu.boot.header.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @className CookieUtils
 * @description 操作cookie的工具类
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/3/7 11:25
 * @version v1.0
 */
@Slf4j
public class CookieUtils {

    /**
     * 获取request中所有的cookie
     * @author kangxu [xukang@engine3d.com]
     * @date 2019/3/7 13:38
     * @param
     * @return javax.servlet.http.Cookie[]
     */
    public static Cookie[] getCookies() {
        HttpServletRequest request = WebUtils.getRequest();
        Cookie[] c = request.getCookies();
        return c;
    }

    /**
     * 通过名称获取cookie
     * @author kangxu [xukang@engine3d.com]
     * @date 2019/3/7 13:41
     * @param name  名称
     * @return java.lang.String
     */
    public static String getCookie(String name) {
        try {

            Cookie[] cookies = getCookies();

            for (int i = 0, len = (cookies == null ? 0 : cookies.length); i < len; i++) {

                Cookie cookie = cookies[i];

                if (name.equalsIgnoreCase(cookie.getName())) {
                    return URLDecoder.decode(cookie.getValue(), "UTF-8");
                }
            }
        } catch (Exception e) {
            log.error("获取cookie失败", e);
        }
        return null;
    }

}
