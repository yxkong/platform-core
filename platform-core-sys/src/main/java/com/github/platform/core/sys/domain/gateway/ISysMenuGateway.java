package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.sys.domain.context.SysMenuContext;
import com.github.platform.core.sys.domain.context.SysMenuQueryContext;
import com.github.platform.core.sys.domain.dto.SysMenuDto;
import com.github.platform.core.sys.domain.dto.resp.RouterDto;
import com.github.platform.core.sys.domain.dto.resp.TreeSelectDto;

import java.util.List;

/**
 * 菜单网关
 * @author: yxkong
 * @date: 2023/2/9 5:32 下午
 * @version: 1.0
 */
public interface ISysMenuGateway {

    /**
     * 获取菜单信息
     * @return
     */
    List<RouterDto> getRouters();

    /**
     * 查询用户菜单树,包含应用，目录和菜单
     * @param hidden 是否隐藏 按钮和接口
     * @return
     */
    List<TreeSelectDto> findUserMenuTree(Boolean hidden);
    /**
     * 新增菜单数据
     * @param context
     * @return
     */
    void insert(SysMenuContext context);

    /**
     * 重置管理员菜单权限
     * @return
     */
    void reloadPermission();

    /**
     * 查询菜单列表
     * @param context
     * @return
     */
    List<SysMenuDto> query(SysMenuQueryContext context);

    /**
     * 修改菜单数据
     * @param context
     * @return
     */
    void update(SysMenuContext context);

    /**
     * 删除菜单数据
     * @param id
     * @return
     */
    void delete(Long id);


    /**
     * 查询菜单详情
     * @param id
     * @return
     */
    SysMenuDto findById(Long id);

    /**
     * 获取角色菜单
     * @param roleIds
     * @return
     */
    List<SysMenuDto> findRolesMenu(List<Long> roleIds);
}
