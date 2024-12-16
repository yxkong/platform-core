package com.github.platform.core.sys.application.executor.impl;

import com.github.platform.core.auth.application.executor.SysExecutor;
import com.github.platform.core.sys.application.executor.IMenuExecutor;
import com.github.platform.core.sys.domain.constant.MenuConstant;
import com.github.platform.core.sys.domain.context.SysMenuContext;
import com.github.platform.core.sys.domain.context.SysMenuQueryContext;
import com.github.platform.core.sys.domain.dto.SysMenuDto;
import com.github.platform.core.sys.domain.dto.resp.RouterDto;
import com.github.platform.core.sys.domain.dto.resp.TreeSelectDto;
import com.github.platform.core.sys.domain.gateway.ISysMenuGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单执行器
 *
 * @author: yxkong
 * @date: 2023/2/7 5:32 下午
 * @version: 1.0
 */
@Service
@Slf4j
public class SysMenuExecutorImpl extends SysExecutor implements IMenuExecutor {
    @Resource
    private ISysMenuGateway menuGateway;

    /**
     * 获取路由菜单
     * @return
     */
    @Override
    public List<RouterDto> getRouters() {
        return menuGateway.getRouters();
    }

    /**
     * 获取菜单下拉列表
     *
     * @return
     */
    @Override
    public List<TreeSelectDto> menuTree() {
        List<TreeSelectDto> list = menuGateway.findUserMenuTree(Boolean.TRUE);
        TreeSelectDto  rootTree = TreeSelectDto.builder().id(MenuConstant.ROOT_ID).label("主类目").build();
        rootTree.setChildren(list);
        List<TreeSelectDto> root = new ArrayList<>();
        root.add(rootTree);
        return root;
    }

    /**
     * 授权菜单，包含所有
     * @return
     */
    @Override
    public List<TreeSelectDto> authMenuTree() {
        return menuGateway.findUserMenuTree(Boolean.FALSE);
    }
    @Override
    public void insert(SysMenuContext context) {
        context.setTenantId(getTenantId(context));
        menuGateway.insert(context);
    }
    @Override
    public void reloadPermission() {
        menuGateway.reloadPermission();
    }
    @Override
    public List<SysMenuDto> query(SysMenuQueryContext context) {
        return menuGateway.query(context);
    }
    @Override
    public void update(SysMenuContext context) {
        context.setTenantId(getTenantId(context));
        menuGateway.update(context);
    }
    @Override
    public void delete(Long id) {
        menuGateway.delete(id);
    }
    @Override
    public SysMenuDto findById(Long id) {
        return menuGateway.findById(id);
    }


}
