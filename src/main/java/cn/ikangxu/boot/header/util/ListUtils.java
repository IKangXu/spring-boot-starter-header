/*
 * Engine3D Technologies Co., Ltd. Copyright 2019, All rights reserved
 */

package cn.ikangxu.boot.header.util;

import java.util.List;

/**
 *
 * @className ListUtils
 * @description List相关操作封装
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/3/7 11:25
 * @version v1.0
 */
public class ListUtils {

    /**
     * 判断 List是否不为空 
     * @author kangxu [xukang@engine3d.com]
     * @date 2019/3/7 16:01
     * @param list 集合
     * @return boolean
     */
    public static <T> boolean isNotEmpty(List<T> list) {
        if (list != null && list.size() != 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断 list 是否为空
     * @author kangxu [xukang@engine3d.com]
     * @date 2019/3/7 16:02
     * @param list  集合
     * @return boolean
     */
    public static <T> boolean isEmpty(List<T> list) {
        if (list != null && list.size() != 0) {
            return false;
        }
        return true;
    }

    /**
     * List 转 array
     * @author kangxu [xukang@engine3d.com]
     * @date 2019/3/7 16:02
     * @param list  集合
     * @return java.lang.Object[]
     */
    public static <T> Object[] toArray(List<T> list) {
        if (ListUtils.isNotEmpty(list)) {
            return list.toArray();
        }
        return null;
    }

    /**
     * 如果相等 加标识
     * @author kangxu [xukang@engine3d.com]
     * @date 2019/3/7 16:02
     * @param strList   字符串集合
     * @return java.util.List<java.lang.String>
     */
    public static List<String> changeSameVal(List<String> strList) {

        for (int i = 0; i < strList.size() - 1;) {
            Integer cnt = 1;
            for (int j = i + 1; j < strList.size();) {
                if (strList.get(i).equals(strList.get(j))) {
                    strList.set(j, strList.get(i).toString() + cnt.toString());

                    cnt++;
                }
                j++;
            }
            i++;
        }

        return strList;
    }

}
