/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header.feign;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignContext;

import feign.Client;

/**
 *
 * @className HeaderFeignContext
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/6/28 8:31
 * @version v1.0
 */
public class HeaderFeignContext extends FeignContext {

    private final HeaderFeignObjectWrapper headerFeignObjectWrapper;
    private final FeignContext delegate;

    HeaderFeignContext(HeaderFeignObjectWrapper headerFeignObjectWrapper,
                       FeignContext delegate) {
        this.headerFeignObjectWrapper = headerFeignObjectWrapper;
        this.delegate = delegate;
    }

    @Override
    public <T> T getInstance(String name, Class<T> type) {
        T object = this.delegate.getInstance(name, type);
        if (object instanceof Client) {
            return object;
        }
        return (T) this.headerFeignObjectWrapper.wrap(object);
    }

    @Override
    public <T> Map<String, T> getInstances(String name, Class<T> type) {
        Map<String, T> instances = this.delegate.getInstances(name, type);
        if (instances == null) {
            return null;
        }
        Map<String, T> convertedInstances = new HashMap<>();
        for (Map.Entry<String, T> entry : instances.entrySet()) {
            if (entry.getValue() instanceof Client) {
                convertedInstances.put(entry.getKey(), entry.getValue());
            }
            else {
                convertedInstances.put(entry.getKey(),
                        (T) this.headerFeignObjectWrapper.wrap(entry.getValue()));
            }
        }
        return convertedInstances;
    }
}
