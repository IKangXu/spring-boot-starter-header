/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header;

import lombok.Data;

import java.util.*;

/**
 *
 * @className ThreadLocalHeaderContext
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/10/18 13:18
 * @version v1.0
 */
@Data
public class GlobalAttributeContext implements AttributeContext {

    private Set<String> globalHeader = new HashSet<>();

    @Override
    public synchronized boolean add(String val) {
        return this.globalHeader.add(val);
    }

    @Override
    public String[] list() {
        String[] list = new String[]{};
        return this.globalHeader.toArray(list);
    }

    @Override
    public String get(String val) {
        if(this.globalHeader.contains(val)) {
            return val;
        }
        return null;
    }

    @Override
    public boolean remove(String val) {
        return this.globalHeader.remove(val);
    }
}
