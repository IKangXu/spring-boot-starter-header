/*
 * Engine3D Technologies Co., Ltd. Copyright 2019, All rights reserved
 */

package cn.ikangxu.boot.header.feign;

import java.io.IOException;
import java.util.*;

import cn.ikangxu.boot.header.RootAttribute;
import cn.ikangxu.boot.header.util.CookieUtils;
import cn.ikangxu.boot.header.util.WebUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.StringUtils;

import feign.Client;
import feign.Request;
import feign.Response;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @className HeaderFeignClient
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/6/28 8:30
 * @version v1.0
 */
public class HeaderFeignClient implements Client {

    private final Client delegate;
    private final BeanFactory beanFactory;

    HeaderFeignClient(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.delegate = new Client.Default(null, null);
    }

    HeaderFeignClient(BeanFactory beanFactory, Client delegate) {
        this.delegate = delegate;
        this.beanFactory = beanFactory;
    }

    @Override
    public Response execute(Request request, Request.Options options) throws IOException {

        Request modifiedRequest = getModifyRequest(request);

        try {
            return this.delegate.execute(modifiedRequest, options);
        } finally {

        }
    }

    private Request getModifyRequest(Request request) {

        String[] keys = RootAttribute.list();
        if(null == keys || keys.length == 0) {
            return request;
        }

        Map<String, Collection<String>> headers = new HashMap<>();
        headers.putAll(request.headers());

        HttpServletRequest servletRequest = WebUtils.getRequest();

        for(int i= 0; i < keys.length; i++) {
            String key = keys[i];

            // 从header中获取token
            String val = servletRequest.getHeader(key);

            // 如果header中不存在token，则从COOKIE中获取
            if (StringUtils.isEmpty(val)) {
                val = CookieUtils.getCookie(key);
            }

            // 如果header中不存在token，则从参数中获取token
            if (StringUtils.isEmpty(val)) {
                val = servletRequest.getParameter(key);
            }
            if(StringUtils.isEmpty(val)) {
                List<String> headerVal = new ArrayList<>();
                headerVal.add(key);
                headers.put(key, headerVal);
            }
        }

        return Request.create(request.method(), request.url(), headers, request.body(), request.charset());
    }

}
