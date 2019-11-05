/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header.feign;

import org.springframework.beans.factory.BeanFactory;

import feign.Feign;

/**
 *
 * @className HeaderFeignBuilder
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/6/28 8:30
 * @version v1.0
 */
final class HeaderFeignBuilder {

    private HeaderFeignBuilder() {
    }

    static Feign.Builder builder(BeanFactory beanFactory) {
        return Feign.builder().client(new HeaderFeignClient(beanFactory));
    }
}

