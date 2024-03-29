package com.github.platform.core.standard.constant;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 操作日志常量
 * @author: yxkong
 * @date: 2023/8/25 12:18 PM
 * @version: 1.0
 */
public interface OptLogConstant {
    /**操作日志默认掩码字段*/
    Set<String> defaultMaskFields =  Sets.newHashSet("password","pwd","oldPwd","newPwd");

    /**默认包含的请求头*/
    Set<String> includeHeader = Sets.newHashSet("referer","host","content-type","x-tenant-id","label","user-agent","token","tenantId");
}
