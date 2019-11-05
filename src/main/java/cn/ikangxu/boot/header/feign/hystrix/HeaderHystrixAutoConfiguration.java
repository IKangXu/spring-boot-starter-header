/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header.feign.hystrix;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.hystrix.HystrixCommand;

/**
 *
 * @className HeaderHystrixAutoConfiguration
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/6/28 8:32
 * @version v1.0
 */
@Configuration
@ConditionalOnClass(HystrixCommand.class)
public class HeaderHystrixAutoConfiguration {

    @Bean
    HeaderHystrixConcurrencyStrategy headerHystrixConcurrencyStrategy() {
        return new HeaderHystrixConcurrencyStrategy();
    }

}
