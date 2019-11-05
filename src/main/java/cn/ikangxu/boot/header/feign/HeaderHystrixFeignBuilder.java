/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header.feign;

import org.springframework.beans.factory.BeanFactory;

import feign.Feign;
import feign.Retryer;
import feign.hystrix.HystrixFeign;

/**
 *
 * @className HeaderHystrixFeignBuilder
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/6/28 8:31
 * @version v1.0
 */
final class HeaderHystrixFeignBuilder {
    private HeaderHystrixFeignBuilder() {
    }

    static Feign.Builder builder(BeanFactory beanFactory) {
        return HystrixFeign.builder().retryer(Retryer.NEVER_RETRY)
                .client(new HeaderFeignClient(beanFactory));
    }
}
