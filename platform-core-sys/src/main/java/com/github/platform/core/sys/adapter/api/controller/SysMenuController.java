package com.github.platform.core.sys.adapter.api.controller;

import com.github.platform.core.auth.annotation.RequiredLogin;
import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.validate.Modify;
import com.github.platform.core.sys.adapter.api.command.menu.SysMenuCmd;
import com.github.platform.core.sys.adapter.api.command.menu.SysMenuQuery;
import com.github.platform.core.sys.adapter.api.convert.SysMenuAdapterConvert;
import com.github.platform.core.sys.application.executor.IMenuExecutor;
import com.github.platform.core.sys.domain.context.SysMenuContext;
import com.github.platform.core.sys.domain.context.SysMenuQueryContext;
import com.github.platform.core.sys.domain.dto.SysMenuDto;
import com.github.platform.core.sys.domain.dto.resp.RouterDto;
import com.github.platform.core.sys.domain.dto.resp.TreeSelectDto;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.groups.Default;
import java.util.List;

/**
 * 菜单信息
 *
 * @author yxkong
 */
@RestController
@Tag(name = "menu",description = "菜单相关")
@RequestMapping(value = "/sys/menu", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class SysMenuController extends BaseController {

    @Resource
    private IMenuExecutor menuExecutor;
    @Resource
    private SysMenuAdapterConvert convert;

    /**
     * 获取路由信息，用于左侧菜单显示
     * @return 路由信息
     */
    @RequiredLogin
    @OptLog(module="menu",title="获取路由信息",persistent = false)
    @Operation(summary = "获取路由信息",tags = {"menu"})
    @PostMapping("/getRouters")
    public ResultBean<List<RouterDto>> getRouters() {
        List<RouterDto> routers = menuExecutor.getRouters();
        return buildSucResp(routers);
    }

    /**
     * 查询权限菜单树，包含所有
     */
    @RequiredLogin
    @OptLog(module="menu",title="查询权限菜单树，包含所有",persistent = false)
    @Operation(summary = "查询权限菜单树",tags = {"menu"})
    @PostMapping("/authMenuTree")
    public ResultBean<List<TreeSelectDto>> authMenuTree() {
        List<TreeSelectDto> dtos = menuExecutor.authMenuTree();
        return buildSucResp(dtos);
    }
    /**
     * 查询菜单树，屏蔽了按钮，用于菜单的维护
     */
    @RequiredLogin
    @OptLog(module="menu",title="查询权限菜单树，包含所有",persistent = false)
    @Operation(summary = "查询菜单树，屏蔽了按钮，用于菜单的维护",tags = {"menu"})
    @PostMapping("/menuTree")
    public ResultBean<List<TreeSelectDto>> menuTree() {
        List<TreeSelectDto> treeSelectDtos = menuExecutor.menuTree();
        return buildSucResp(treeSelectDtos);
    }
    /**
     * 新增菜单
     */
    @OptLog(module="menu",title="新增菜单",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增菜单",tags = {"menu"})
    @PostMapping("/add")
    public ResultBean add(@RequestBody @Validated SysMenuCmd cmd) {
        SysMenuContext context = convert.toContext(cmd);
        menuExecutor.insert(context);
        return buildSucResp();
    }

    /**
     * 重新分配权限菜单
     */
    @OptLog(module="menu",title="重新分配权限菜单",optType = LogOptTypeEnum.modify)
    @Operation(summary = "重新分配权限菜单",tags = {"menu"})
    @PostMapping("/reloadPermission")
    public ResultBean reloadPermission() {
        menuExecutor.reloadPermission();
        return buildSucResp();
    }


    /**
     * 菜单列表查询
     *
     * @param menuQuery
     * @return
     */
    @OptLog(module="menu",title="菜单列表查询",persistent = false)
    @Operation(summary = "菜单列表查询",tags = {"menu"})
    @PostMapping("/query")
    public ResultBean<List<SysMenuDto>> query(@RequestBody SysMenuQuery menuQuery) {
        SysMenuQueryContext context = convert.toQuery(menuQuery);
        List<SysMenuDto> list = menuExecutor.query(context);
        return buildSucResp(list);
    }

    /**
     * 修改菜单
     *
     * @param cmd
     * @return
     */
    @OptLog(module="menu",title="修改菜单",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改菜单",tags = {"menu"})
    @PostMapping("/modify")
    public ResultBean modify(@RequestBody @Validated({Modify.class, Default.class}) SysMenuCmd cmd) {
        SysMenuContext context = convert.toContext(cmd);
        menuExecutor.update(context);
        return buildSucResp();
    }

    /**
     * 删除菜单
     *
     * @param idReq
     * @return
     */
    @OptLog(module="menu",title="删除菜单",optType = LogOptTypeEnum.delete)
    @PostMapping("/delete")
    @Operation(summary = "删除菜单",tags = {"menu"})
    public ResultBean<?> delete(@RequestBody @Validated StrIdReq idReq) {
        menuExecutor.delete(idReq.getId());
        return buildSucResp();
    }

    /**
     * 查询菜单详情
     *
     * @param idReq
     * @return
     */
    @OptLog(module="menu",title="查询菜单详情",persistent = false)
    @Operation(summary = "查询菜单详情",tags = {"menu"})
    @PostMapping("/menuDetail")
    public ResultBean<SysMenuDto> menuDetail(@RequestBody @Validated StrIdReq idReq) {
        SysMenuDto menuDto = menuExecutor.findById(idReq.getId());
        return buildSucResp(menuDto);
    }


}