/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header.util;

import cn.ikangxu.boot.header.common.request.httpclient.HttpClientTemplate;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author kangxu [xukang@engine3d.com]
 * @version v1.0
 * @className SpringContextUtils
 * @description
 * @date 2019/11/7 9:24
 */
@Component("springContextUtils")
public class SpringContextUtils implements ApplicationContextAware {
    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        return null == applicationContext ? null : applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return null == applicationContext ? null : applicationContext.getBean(name, requiredType);
    }

    public static boolean containsBean(String name) {
        return null == applicationContext ? false : applicationContext.containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return null == applicationContext ? false : applicationContext.isSingleton(name);
    }

    public static Object getProperty(String key) {
        return null == applicationContext ? null : applicationContext.getEnvironment().getProperty(key);
    }

    public static Class<? extends Object> getType(String name) {
        return null == applicationContext ? null : applicationContext.getType(name);
    }

}