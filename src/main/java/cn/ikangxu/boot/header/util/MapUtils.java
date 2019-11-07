/*
 * Engine3D Technologies Co., Ltd. Copyright 2019, All rights reserved
 */

package cn.ikangxu.boot.header.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @className MapUtils
 * @description 处理Map集合
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/3/7 11:25
 * @version v1.0
 */
public class MapUtils {

    /**
     * Map 集合是否不为空
     * @author kangxu [xukang@engine3d.com]
     * @date 2019/3/7 16:03
     * @param map   集合
     * @return boolean
     */
    public static <T> boolean isNotEmpty(Map<String, T> map) {
        if (map != null && map.size() != 0) {
            return true;
        }
        return false;
    }

    /**
     * Map 集合是否为空
     * @author kangxu [xukang@engine3d.com]
     * @date 2019/3/7 16:04
     * @param map   集合
     * @return boolean
     */
    public static <T> boolean isEmpty(Map<String, T> map) {
        if (map != null && map.size() != 0) {
            return false;
        }
        return true;
    }

    /**
     * 获取 所有key
     * @author kangxu [xukang@engine3d.com]
     * @date 2019/3/7 16:04
     * @param map   集合
     * @return java.util.List<java.lang.String>
     */
    public static <T> List<String> getKeys(Map<String, T> map) {
        List<String> str = new ArrayList<String>();
        for (Entry<String, T> entry : map.entrySet()) {
            str.add(entry.getKey());
        }
        return str;
    }

    /**
     * 获取所有的value
     * @author kangxu [xukang@engine3d.com]
     * @date 2019/3/7 16:04
     * @param map   集合
     * @return java.util.List<T>
     */
    public static <T> List<T> getValues(Map<String, T> map) {
        List<T> str = new ArrayList<T>();
        for (Entry<String, T> entry : map.entrySet()) {
            str.add(entry.getValue());
        }
        return str;
    }

    /**
     * 将Object 转 Map
     * @author kangxu [xukang@engine3d.com]
     * @date 2019/3/7 16:04
     * @param obj   需要转换的对象
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public static <T> Map<String, Object> objectToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }

            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Map 转 Object
     * @author kangxu [xukang@engine3d.com]
     * @date 2019/3/7 16:05
     * @param map           需要转换的Map
     * @param beanClass     映射的对象
     * @return java.lang.Object
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) throws Exception {
        if (map == null)
            return null;

        T obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }

            field.setAccessible(true);
            if ("java.util.Date".equals(field.getType().getName()) && null != map.get(field.getName())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(Long.valueOf(map.get(field.getName()).toString()));
                field.set(obj, date);
            } else
                field.set(obj, map.get(field.getName()));
        }

        return obj;
    }

    /**
     * 新建一个HashMap
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @return HashMap对象
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }
}
