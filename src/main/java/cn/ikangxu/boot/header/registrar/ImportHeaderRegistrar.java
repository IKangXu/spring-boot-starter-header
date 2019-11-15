/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header.registrar;

import cn.ikangxu.boot.header.RootAttribute;
import cn.ikangxu.boot.header.annotation.EnableHeader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author kangxu [xukang@engine3d.com]
 * @version v1.0
 * @className ImportHeaderRegistrar
 * @description
 * @date 2019/10/18 11:14
 */
public class ImportHeaderRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(EnableHeader.class.getName(), false));
        if (null != annotationAttributes) {
            String[] values = annotationAttributes.getStringArray("value");

            RootAttribute.bind(values);
        }
    }
}
