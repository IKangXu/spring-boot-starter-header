/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @className RootHeader
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/10/18 14:27
 * @version v1.0
 */
public class RootHeader {

    private static HeaderContext CONTEXT_HOLDER = HeaderContextLoader.load();

    public static void bind(String key, String val) {
        CONTEXT_HOLDER.put(key, val);
    }

    public static Map list() {
        return CONTEXT_HOLDER.list();
    }

    public static String get(String key){
        return CONTEXT_HOLDER.get(key);
    }

    public static void unbind() {
        Map list = list();

        Set set = list.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Object key = iterator.next();
            CONTEXT_HOLDER.remove(key.toString());
        }
    }

}
