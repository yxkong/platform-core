package com.github.platform.core.gateway.admin.adapter.api.controller;

import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.gateway.admin.adapter.api.command.GatewayRouteInfoCmd;
import com.github.platform.core.gateway.admin.adapter.api.command.GatewayRouteQuery;
import com.github.platform.core.gateway.admin.adapter.api.convert.GatewayRouteAdapterConvert;
import com.github.platform.core.gateway.admin.application.executor.IGatewayRouteExecutor;
import com.github.platform.core.gateway.admin.domain.context.GatewayRouteQueryContext;
import com.github.platform.core.gateway.admin.domain.dto.GatewayRouteDto;
import com.github.platform.core.gateway.admin.domain.dto.GatewayRouteInfoDto;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
/**
 * 网关路由
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-13 14:58:53.823
 * @version 1.0
 */
@RestController
@Tag(name = "gatewayRoutes",description = "网关路由管理")
@RequestMapping("api/sys/gateway/route")
@Slf4j
public class GatewayRouteController extends BaseController{
    @Resource
    private IGatewayRouteExecutor gatewayRouteExecutor;
    @Resource
    private GatewayRouteAdapterConvert gatewayRouteConvert;

    /**
    * 查询网关路由列表
    * @param query 查询实体
    * @return 分页结果
    */
    @OptLog(module="gatewayRoutes",title="查询网关路由列表",persistent = false)
    @Operation(summary = "查询网关路由列表",tags = {"gatewayRoutes"})
    @PostMapping("/query")
    public ResultBean<PageBean<GatewayRouteDto>> query(@RequestBody GatewayRouteQuery query){
        GatewayRouteQueryContext context = gatewayRouteConvert.toQuery(query);
        PageBean<GatewayRouteDto> pageBean = gatewayRouteExecutor.query(context);
        return buildSucResp(pageBean);
    }

    /**
    * 新增网关路由
    * @param cmd 新增实体
    * @return 操作结果
    */
    @OptLog(module="gatewayRoutes",title="新增网关路由",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增网关路由",tags = {"gatewayRoutes"})
    @PostMapping("/add")
    public ResultBean<String> add(@Validated @RequestBody GatewayRouteInfoCmd cmd) {
        String id = gatewayRouteExecutor.insertInfo(gatewayRouteConvert.toRouteInfo(cmd));
        return buildSucResp(id);
    }

    /**
    * 根据id查询网关路由明细
    * @param id 主键id
    * @return 单条记录
    */
    @OptLog(module="gatewayRoutes",title="根据id查询网关路由明细",optType = LogOptTypeEnum.detail,persistent = false)
    @Operation(summary = "根据id查询网关路由明细",tags = {"gatewayRoutes"})
    @PostMapping("/detail")
    public ResultBean<GatewayRouteInfoDto> detail(@Validated @RequestBody StrIdReq id) {
        GatewayRouteInfoDto dto = gatewayRouteExecutor.findRouteInfo(id.getId());
        return buildSucResp(dto);
    }

    /**
     * 根据id删除网关路由记录
     * @param id 主键id
     * @return 操作结果
     */
    @OptLog(module="gatewayRoutes",title="根据id删除网关路由记录",optType = LogOptTypeEnum.delete)
    @Operation(summary = "根据id删除网关路由记录",tags = {"gatewayRoutes"})
    @PostMapping("/delete")
    public ResultBean<?> delete(@Validated @RequestBody StrIdReq id) {
        gatewayRouteExecutor.delete(id.getId());
        return buildSucResp();
    }

    /**
     * 修改网关路由
     * @param cmd 修改实体
     * @return 操作结果
     */
    @OptLog(module="gatewayRoutes",title="修改网关路由",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改网关路由",tags = {"gatewayRoutes"})
    @PostMapping("/modify")
    public ResultBean modify(@Validated @RequestBody GatewayRouteInfoCmd cmd) {
        gatewayRouteExecutor.updateInfo(gatewayRouteConvert.toRouteInfo(cmd));
        return buildSucResp();
    }
}