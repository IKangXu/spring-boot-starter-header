/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @className RootAttribute
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/10/18 13:41
 * @version v1.0
 */
@Slf4j
public class RootAttribute {

    private static AttributeContext HEADER_ATTRIBUTE_CONTEXT = AttributeContextLoader.load();

    public static void bind(String key) {
        HEADER_ATTRIBUTE_CONTEXT.add(key);
    }

    public static void bind(Collection<String> keys) {
        if(null == keys || keys.isEmpty()) {
            log.info("没有全局参数绑定");
            return;
        }

        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            HEADER_ATTRIBUTE_CONTEXT.add(key);
        }
    }

    public static void bind(String[] keys) {
        if(null == keys || keys.length == 0) {
            log.info("没有全局参数绑定");
            return;
        }
        for(String key : keys) {
            HEADER_ATTRIBUTE_CONTEXT.add(key);
        }
    }

    public static String[] list() {
        return HEADER_ATTRIBUTE_CONTEXT.list();
    }

}
