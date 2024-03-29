package com.github.platform.core.common.utils;

/**
 * @author Qiuxinchao
 * @date 2023/2/27 13:41
 * @describe
 */
public class ObjectUtils {

    /**
     * 判断不是都为空 至少有一个不为空
     * @param objects
     * @return
     */
    public static boolean notAllNull(Object... objects){
        if (objects == null){
            return true;
        }
        int sum = 0;
        for (Object object : objects){
            if (object == null){
                sum++;
            }
        }
        return sum != objects.length;
    }

    /**
     * 任意为空
     * @param objects
     * @return
     */
    public static boolean anyNull(Object... objects){
        if (objects == null){
            return true;
        }
        for (Object object : objects){
            if (object == null){
                return true;
            }
        }
        return false;
    }

    /**
     * 全部不为空
     * @param objects
     * @return
     */
    public static boolean allNotNull(Object... objects){
        if (objects == null){
            return false;
        }
        for (Object object : objects){
            if (object == null){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否全为空
     * @param objects
     * @return
     */
    public static boolean allNull(Object... objects){
        if (objects == null){
            return true;
        }
        int sum = 0;
        for (Object object : objects){
            if (object == null){
                sum++;
            }
        }
        return sum == objects.length;
    }

    /**
     * 先校验前两个是否不全空，在校验后续是否不全为空
     * @param objects
     * @return
     */
    public static boolean orAndNotAllNull(Object... objects){
        if (objects.length < 2){return false;}
        if (objects[0] == null && objects[1] == null){return false;}
        int sum = 0;
        for (int i=2; i<objects.length; i++){
            if (objects[i] == null){
                sum++;
            }
        }
        return sum != objects.length -2;
    }

}
