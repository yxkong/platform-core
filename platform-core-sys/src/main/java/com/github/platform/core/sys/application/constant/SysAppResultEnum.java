package com.github.platform.core.sys.application.constant;

import com.github.platform.core.standard.exception.BaseResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 适配应用层枚举
 * @author: yxkong
 * @date: 2023/12/22 14:53
 * @version: 1.0
 */
@Getter
@AllArgsConstructor
public enum SysAppResultEnum implements BaseResult {
    LOGIN_ERROR("2201","登录异常！"),
    USER_NOT_FOUND("2202","用户未找到" ),
    ADMIN_TENANT_NO_AUTH("2203","暂无操作其他租户数据的权限！" )
    ;


    private String status;
    private String message;
}
