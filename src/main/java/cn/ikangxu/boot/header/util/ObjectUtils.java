/*
 * Engine3D Technologies Co., Ltd. Copyright 2019, All rights reserved
 */

package cn.ikangxu.boot.header.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 *
 * @className ObjectUtils
 * @description 对象操作工具
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/3/7 11:25
 * @version v1.0
 */
public class ObjectUtils {

    public static boolean isNotEmpty(Object object) {
        if (null == object) {
            return false;
        }
        if (object instanceof Integer || object instanceof Boolean) {
            return true;
        }
        if (object instanceof String) {
            if ("".equals(object)) {
                return false;
            }
            return true;
        }
        if (object instanceof List) {
            if (((List)object).isEmpty()) {
                return false;
            }
            return true;
        }
        if (object instanceof Map) {
            if (((Map)object).isEmpty()) {
                return false;
            }
            return true;
        }
        if (null != object) {
            return true;
        }
        return false;
    }

    /**
     * 将老对象的属性值赋值给新对象
     * @author kangxu [xukang@engine3d.com]
     * @date 2019/3/7 16:09
     * @param oldObj    老对象
     * @param newObj    新对象
     * @return T
     */
    public static <T> T restBuildObj(T oldObj, T newObj) throws IllegalAccessException {
        Field[] oldFields = oldObj.getClass().getDeclaredFields();
        for (int i = 0, size = oldFields.length; i < size; i++) {
            Field field = oldFields[i];
            field.setAccessible(true);
            String name = field.getName();
            if ("serialVersionUID".equals(name)) {
                continue;
            }
            Class<?> type = field.getType();
            Object o = field.get(oldObj);
            if ("java.lang.String".equals(type.getName())) {
                if (ObjectUtils.isNotEmpty(o)) {
                    field.set(newObj, o);
                }
            } else {
                if (null != o) {
                    field.set(newObj, o);
                }
            }
        }
        return newObj;
    }
}
