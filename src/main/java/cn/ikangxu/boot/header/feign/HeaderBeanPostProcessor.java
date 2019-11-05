/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header.feign;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 *
 * @className HeaderBeanPostProcessor
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/6/28 8:29
 * @version v1.0
 */
public class HeaderBeanPostProcessor implements BeanPostProcessor {

    private final HeaderFeignObjectWrapper headerFeignObjectWrapper;

    HeaderBeanPostProcessor(HeaderFeignObjectWrapper headerFeignObjectWrapper) {
        this.headerFeignObjectWrapper = headerFeignObjectWrapper;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        return this.headerFeignObjectWrapper.wrap(bean);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }
}