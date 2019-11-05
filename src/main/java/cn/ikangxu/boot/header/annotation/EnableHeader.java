/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header.annotation;

import cn.ikangxu.boot.header.registrar.ImportHeaderRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *
 * @className EnableHeader
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/10/18 11:11
 * @version v1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ImportHeaderRegistrar.class)
public @interface EnableHeader {
    String[] value() default {};
}
