package com.github.platform.core.dingtalk.domain.gateway;

/**
 * 钉钉操作网关
 * @author: yxkong
 * @date: 2024/1/19 17:03
 * @version: 1.0
 */
public interface IDingTalkGateway {
    /**
     *  初始化部门信息
     * @param deptId 指定部门，为null全部
     * @param tenantId 指定租户
     * @param optUser 操作用户
     */
    void initDept(Long deptId,Integer tenantId,String optUser);

    /**
     * 初始化指定租户用户
     * @param tenantId
     * @param optUser 操作用户
     */
    void initAllUser(Integer tenantId,String optUser);
}
