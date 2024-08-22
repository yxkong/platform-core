package com.github.platform.core.gateway.admin.application.executor;

import com.github.platform.core.gateway.admin.domain.context.GatewayRouteContext;
import com.github.platform.core.gateway.admin.domain.context.GatewayRouteInfoContext;
import com.github.platform.core.gateway.admin.domain.context.GatewayRouteQueryContext;
import com.github.platform.core.gateway.admin.domain.dto.GatewayRouteDto;
import com.github.platform.core.gateway.admin.domain.dto.GatewayRouteInfoDto;
import com.github.platform.core.standard.entity.dto.PageBean;

/**
 * 网关路由执行器接口
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-13 14:58:53.823
 * @version 1.0
 */
public interface IGatewayRouteExecutor {
    /**
    * 查询网关路由列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<GatewayRouteDto> query(GatewayRouteQueryContext context);
    /**
    * 新增网关路由
    * @param context 新增上下文
    */
    String insertInfo(GatewayRouteInfoContext context);
    /**
    * 根据id查询网关路由明细
    * @param id 主键
    * @return 单条记录
    */
    GatewayRouteDto findById(Long id);
    /**
    * 修改网关路由
    * @param context 更新上下文
    */
    void updateInfo(GatewayRouteInfoContext context);
    /**
    * 根据id删除网关路由记录
    * @param id 主键
    */
    void delete(Long id);

    /**
     * 根据id查询网关路由信息
     * @param id
     * @return
     */
    GatewayRouteInfoDto findRouteInfo(Long id);
}