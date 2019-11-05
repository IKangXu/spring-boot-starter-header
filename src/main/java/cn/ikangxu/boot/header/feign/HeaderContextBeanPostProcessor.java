/*
 * Engine3D Technologies Co., Ltd. Copyright 2019, All rights reserved
 */

package cn.ikangxu.boot.header.feign;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.openfeign.FeignContext;

/**
 *
 * @className HeaderContextBeanPostProcessor
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/6/28 8:29
 * @version v1.0
 */
public class HeaderContextBeanPostProcessor implements BeanPostProcessor {

    private final BeanFactory beanFactory;
    private HeaderFeignObjectWrapper headerFeignObjectWrapper;

    HeaderContextBeanPostProcessor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof FeignContext && !(bean instanceof HeaderFeignClient)) {
            return new HeaderFeignContext(getHeaderFeignObjectWrapper(), (FeignContext)bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private HeaderFeignObjectWrapper getHeaderFeignObjectWrapper() {
        if (this.headerFeignObjectWrapper == null) {
            this.headerFeignObjectWrapper = this.beanFactory.getBean(HeaderFeignObjectWrapper.class);
        }
        return this.headerFeignObjectWrapper;
    }
}
