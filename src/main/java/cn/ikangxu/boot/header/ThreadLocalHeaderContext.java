/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @className ThreadLocalHeaderContext
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/10/18 14:28
 * @version v1.0
 */
public class ThreadLocalHeaderContext implements HeaderContext {

    private ThreadLocal<Map<String, String>> threadLocal = ThreadLocal.withInitial(() -> new HashMap());

    public ThreadLocalHeaderContext() {
    }

    public String put(String key, String value) {
        return (String)((Map)this.threadLocal.get()).put(key, value);
    }

    public String get(String key) {
        return (String)((Map)this.threadLocal.get()).get(key);
    }

    public Map list() {
        return (Map)this.threadLocal.get();
    }

    public String remove(String key) {
        return (String)((Map)this.threadLocal.get()).remove(key);
    }
}
