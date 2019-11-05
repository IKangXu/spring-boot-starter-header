/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header;

/**
 *
 * @className HeaderAttributeContext
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/10/18 13:17
 * @version v1.0
 */
public interface AttributeContext {
    boolean add(String val);

    String[] list();

    String get(String val);

    boolean remove(String val);
}
