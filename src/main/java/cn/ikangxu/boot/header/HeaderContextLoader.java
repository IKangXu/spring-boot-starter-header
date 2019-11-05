/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header;

/**
 *
 * @className HeaderContextLoader
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/10/18 14:27
 * @version v1.0
 */
public class HeaderContextLoader {
    private static HeaderContext INSTANCE;

    public static HeaderContext load() {
        if (null == INSTANCE) {
            INSTANCE = new ThreadLocalHeaderContext();
        }

        return INSTANCE;
    }

    public HeaderContextLoader() {

    }
}
