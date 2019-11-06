/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header;

import java.util.Map;

/**
 *
 * @className HeaderContext
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/10/18 14:26
 * @version v1.0
 */
public interface HeaderContext {
    String put(String key, String val);

    String get(String key);

    Map list();

    String remove(String key);
}
