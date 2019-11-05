/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header;

/**
 *
 * @className HeaderAttributeContextLoader
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/10/18 13:31
 * @version v1.0
 */
public class AttributeContextLoader {

    private static AttributeContext INSTANCE;

    public static AttributeContext load() {
        if (null == INSTANCE) {
            INSTANCE = new GlobalAttributeContext();
        }

        return INSTANCE;
    }

    public AttributeContextLoader() {

    }
}
