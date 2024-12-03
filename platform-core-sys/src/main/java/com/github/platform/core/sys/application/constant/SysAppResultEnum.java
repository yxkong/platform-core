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
    ADMIN_TENANT_NO_AUTH("2203","暂无操作其他租户数据的权限！" ),
    NO_TENANT("2204","管理员，请选择租户以后再查询！" ),
    ROLE_ALREADY_EXIST("2210","该角色名或角色标识在该租户中已存在,请核验"),
    ROLE_ADD_ERROR("2211","添加角色失败，请联系运维人员！"),
    FORBID_DELETE_ROLE("2212","该角色下存在用户，无法删除，请核验"),
    CASCADE_DONT_EXIST("1000","对应父级数据不存在！"),
    ;


    private String status;
    private String message;
}
