package com.github.platform.core.common.utils;

import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 集合
 * @author: yxkong
 * @date: 2023/5/4 1:39 PM
 * @version: 1.0
 */
public class CollectionUtil extends org.springframework.util.CollectionUtils {
    public static boolean isEmpty(final Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }
    public static boolean isNotEmpty(final Collection<?> coll) {
        return !isEmpty(coll);
    }
    public static boolean isEmpty(final Object[]  arrays) {
        return arrays == null || arrays.length == 0;
    }
    public static boolean isNotEmpty(final Object[]  arrays) {
        return !isEmpty(arrays);
    }
    public static boolean isNotEmpty(@Nullable Map<?, ?> map){
        return !isEmpty(map);
    }

    public static boolean isExist(final Map<String, ?> map,String key){
        if (isEmpty(map)){
            return false;
        }
        return map.get(key) != null;
    }
    public static List newListIfNull(List<?> list) {
        if (isEmpty(list)){
            return new ArrayList();
        }
        return list;
    }
}
