/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header.feign;

import java.io.IOException;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient;

import feign.Client;
import feign.Request;
import feign.Response;

/**
 *
 * @className HeaderLoadBalancerFeignClient
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/6/28 8:28
 * @version v1.0
 */
public class HeaderLoadBalancerFeignClient extends LoadBalancerFeignClient {

    private final BeanFactory beanFactory;

    HeaderLoadBalancerFeignClient(Client delegate,
                                  CachingSpringLoadBalancerFactory lbClientFactory,
                                  SpringClientFactory clientFactory, BeanFactory beanFactory) {
        super(wrap(delegate, beanFactory), lbClientFactory, clientFactory);
        this.beanFactory = beanFactory;
    }

    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        return super.execute(request, options);
    }

    private static Client wrap(Client delegate, BeanFactory beanFactory) {
        return (Client) new HeaderFeignObjectWrapper(beanFactory).wrap(delegate);
    }

}
