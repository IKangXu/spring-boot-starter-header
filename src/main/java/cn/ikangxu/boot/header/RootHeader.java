/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header;

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

}
