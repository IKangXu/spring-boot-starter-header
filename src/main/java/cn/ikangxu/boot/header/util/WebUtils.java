/*
 * Engine3D Technologies Co., Ltd. Copyright 2019, All rights reserved
 */

package cn.ikangxu.boot.header.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @className WebUtils
 * @description Web请求操作
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/3/7 11:25
 * @version v1.0
 */
public class WebUtils {

    private WebUtils() {}

    /**
     * 返回当前的请求
     * @author kangxu [xukang@engine3d.com]
     * @date 2019/3/7 16:27
     * @param
     * @return javax.servlet.http.HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
    }

}
