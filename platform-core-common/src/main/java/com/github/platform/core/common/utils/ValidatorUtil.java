package com.github.platform.core.common.utils;

import com.github.platform.core.standard.validate.Default;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
/**
 * javax.validation 校验工具类
 * @author: yxkong
 * @date: 2023/8/10 3:32 PM
 * @version: 1.0
 */
public class ValidatorUtil {
    private static Validator validator = null;
    static {
        // 创建一个 ValidatorFactory
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    public static String validate(Object obj){
        return validate(obj,null);
    }
    public static String validate(Object obj, Class<?>... groups) {
        // 手动校验对象，传入指定的分组
        Set<ConstraintViolation<Object>> violations = null;
        // 默认情况下，如果没有指定分组，则执行默认校验
        if (groups == null || groups.length == 0) {
            groups = new Class<?>[] { Default.class };
        }
        violations = validator.validate(obj, groups);
        // 检查是否有校验错误
        if (CollectionUtil.isNotEmpty(violations)) {
            StringBuilder em = new StringBuilder("Validation error: ");
            for (ConstraintViolation<Object> violation : violations) {
                em.append(violation.getMessage()).append("。");
            }
            return em.toString();
        }
        return null;
    }
}
