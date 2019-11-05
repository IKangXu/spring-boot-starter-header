/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header.feign;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient;

import feign.Client;

/**
 *
 * @className HeaderFeignObjectWrapper
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/6/28 8:31
 * @version v1.0
 */
public class HeaderFeignObjectWrapper {

    private final BeanFactory beanFactory;

    private CachingSpringLoadBalancerFactory cachingSpringLoadBalancerFactory;
    private SpringClientFactory springClientFactory;

    HeaderFeignObjectWrapper(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    Object wrap(Object bean) {
        if (bean instanceof Client && !(bean instanceof HeaderFeignClient)) {
            if (bean instanceof LoadBalancerFeignClient) {
                LoadBalancerFeignClient client = ((LoadBalancerFeignClient) bean);
                return new HeaderLoadBalancerFeignClient(client.getDelegate(), factory(),
                        clientFactory(), this.beanFactory);
            }
            return new HeaderFeignClient(this.beanFactory, (Client) bean);
        }
        return bean;
    }

    CachingSpringLoadBalancerFactory factory() {
        if (this.cachingSpringLoadBalancerFactory == null) {
            this.cachingSpringLoadBalancerFactory = this.beanFactory
                    .getBean(CachingSpringLoadBalancerFactory.class);
        }
        return this.cachingSpringLoadBalancerFactory;
    }

    SpringClientFactory clientFactory() {
        if (this.springClientFactory == null) {
            this.springClientFactory = this.beanFactory
                    .getBean(SpringClientFactory.class);
        }
        return this.springClientFactory;
    }

}
