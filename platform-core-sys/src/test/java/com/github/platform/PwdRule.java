package com.github.platform;

import com.github.platform.core.common.utils.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 密码规则单元测试
 *
 * @author: yxkong
 * @date: 2023/7/21 9:52 AM
 * @version: 1.0
 */
public class PwdRule {
    @Test
    @DisplayName("密码验证")
    public void test(){
        String pwd = "aBc1@x1";
        boolean b = StringUtils.isPwdPattern(pwd);
        Assertions.assertTrue(b);
        pwd = "aBc11x1";
        b = StringUtils.isPwdPattern(pwd);
        Assertions.assertFalse(b);
    }
}
