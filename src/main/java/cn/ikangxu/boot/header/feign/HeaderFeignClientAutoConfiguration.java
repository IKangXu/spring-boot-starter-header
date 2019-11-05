/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header.feign;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import feign.Client;
import feign.Feign;

/**
 *
 * @className HeaderFeignClientAutoConfiguration
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/6/28 8:30
 * @version v1.0
 */
@Configuration
@ConditionalOnClass(Client.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class HeaderFeignClientAutoConfiguration {

    @Bean
    @Scope("prototype")
    @ConditionalOnClass(name = "com.netflix.hystrix.HystrixCommand")
    @ConditionalOnProperty(name = "feign.hystrix.enabled", havingValue = "true")
    Feign.Builder feignHystrixBuilder(BeanFactory beanFactory) {
        return HeaderHystrixFeignBuilder.builder(beanFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    @Scope("prototype")
    Feign.Builder feignBuilder(BeanFactory beanFactory) {
        return HeaderFeignBuilder.builder(beanFactory);
    }

    @Configuration
    protected static class FeignBeanPostProcessorConfiguration {

        @Bean
        HeaderBeanPostProcessor headerBeanPostProcessor(HeaderFeignObjectWrapper headerFeignObjectWrapper) {
            return new HeaderBeanPostProcessor(headerFeignObjectWrapper);
        }

        @Bean
        HeaderContextBeanPostProcessor headerContextBeanPostProcessor(BeanFactory beanFactory) {
            return new HeaderContextBeanPostProcessor(beanFactory);
        }

        @Bean
        HeaderFeignObjectWrapper headerFeignObjectWrapper(BeanFactory beanFactory) {
            return new HeaderFeignObjectWrapper(beanFactory);
        }
    }

}
