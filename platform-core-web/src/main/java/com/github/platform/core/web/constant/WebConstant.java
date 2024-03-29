package com.github.platform.core.web.constant;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * web模块常量
 * @author: yxkong
 * @date: 2023/8/3 2:07 PM
 * @version: 1.0
 */
public class WebConstant {
    public static final Set<String> INCLUDE_HEADERS = Sets.newHashSet("token","loginName","tenantId");
}
