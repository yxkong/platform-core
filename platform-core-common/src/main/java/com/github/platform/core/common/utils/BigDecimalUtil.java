package com.github.platform.core.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * BigDecimal 的常用操作
 */
public class BigDecimalUtil {
    // 零值
    public static final BigDecimal BIGDECIMAL_ZERO = BigDecimal.ZERO;
    // 100
    public static final BigDecimal BIGDECIMAL_HUNDRED = new BigDecimal(100);

    /**
     * 判断数据是否整百
     * @param bigDecimal        需要判断的数据
     * @return                  整百为true 否则为false
     */
    public static boolean isBigDecimalHundreds(BigDecimal bigDecimal){
        BigDecimal bigDecimal1 = bigDecimal.divideAndRemainder(BigDecimalUtil.BIGDECIMAL_HUNDRED)[1];
        return bigDecimal1.compareTo(BigDecimalUtil.BIGDECIMAL_ZERO) == 0;
    }

    /**
     * 截取 整百数值
     * @param bigDecimal        需要截取的数据
     * @return                  截取后的数据
     */
    public static BigDecimal getBigDecimalHundred(BigDecimal bigDecimal){
        if(BigDecimalUtil.BIGDECIMAL_HUNDRED.compareTo(bigDecimal) > 0){
            // 当前数据值小于 100 不做处理
            return bigDecimal;
        }
        // 截取整百的数据
        return bigDecimal.divideAndRemainder(BigDecimalUtil.BIGDECIMAL_HUNDRED)[0].multiply(BigDecimalUtil.BIGDECIMAL_HUNDRED);
    }

    /**
     * 自动去除小数点后方的零值
     * @param bigDecimal        原数据
     * @return                  去除后的字符串
     */
    public static String getBigDecimalString(BigDecimal bigDecimal){
        // 原值 * 100 判断是否整百
        BigDecimal bigDecimalHundred = bigDecimal.multiply(BigDecimalUtil.BIGDECIMAL_HUNDRED);
        if (!isBigDecimalHundreds(bigDecimalHundred)){
            // 当前数据不为整百 不做处理
            return bigDecimal.toPlainString();
        }
        // 当前数据为整百，去除小数点后方的零
        return getBigDecimalStringWithOutPoint(bigDecimal);

    }

    /**
     * 去除数据小数点后面的值
     * @param bigDecimal
     * @return
     */
    public static String getBigDecimalStringWithOutPoint(BigDecimal bigDecimal){
        return bigDecimal.setScale( 0, BigDecimal.ROUND_DOWN ).toPlainString();
    }

    /**
     * Object转BigDecimal类型
     *
     * @param value 要转的object类型
     * @return 转成的BigDecimal类型数据
     */
    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }


    /**
     * 当前数值转成元为单位，保留2为小数；
     * @param amount
     * @return
     */
    public static BigDecimal changeF2Y(BigDecimal amount) {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0){
            return amount.divide(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }
    /**
     * 金额元转分
     * @param amount
     * @return
     */
    public static Integer changeY2F(BigDecimal amount) {
        if (amount == null || amount.equals(BigDecimal.ZERO)) {
            return 0;
        }
        return amount.multiply(new BigDecimal("100")).setScale(2).intValue();
    }
    /**
     * 当前数值转成元为单位，去除小数；
     * @param amount
     * @return
     */
    public static Integer changeY2Y(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0){
            return amount.stripTrailingZeros().intValue();
        }
        return 0;
    }
}
