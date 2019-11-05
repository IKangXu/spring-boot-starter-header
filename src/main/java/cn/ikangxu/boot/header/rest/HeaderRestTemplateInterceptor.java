/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header.rest;

import cn.ikangxu.boot.header.RootAttribute;
import cn.ikangxu.boot.header.RootHeader;
import cn.ikangxu.boot.header.util.CookieUtils;
import cn.ikangxu.boot.header.util.WebUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *
 * @className HeaderRestTemplateInterceptor
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/10/18 14:35
 * @version v1.0
 */
public class HeaderRestTemplateInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
            HttpRequestWrapper requestWrapper = new HttpRequestWrapper(httpRequest);

        String[] keys = RootAttribute.list();
        if(null == keys || keys.length == 0) {
            return clientHttpRequestExecution.execute(requestWrapper, bytes);
        }

        HttpServletRequest request = WebUtils.getRequest();

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
            if(StringUtils.isEmpty(val)) {
                requestWrapper.getHeaders().add(key, val);
            }
        }

        return clientHttpRequestExecution.execute(requestWrapper, bytes);
    }
}
