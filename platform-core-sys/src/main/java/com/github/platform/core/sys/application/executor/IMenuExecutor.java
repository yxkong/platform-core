package com.github.platform.core.sys.application.executor;

import com.github.platform.core.sys.domain.context.SysMenuContext;
import com.github.platform.core.sys.domain.context.SysMenuQueryContext;
import com.github.platform.core.sys.domain.dto.SysMenuDto;
import com.github.platform.core.sys.domain.dto.resp.RouterDto;
import com.github.platform.core.sys.domain.dto.resp.TreeSelectDto;

import java.util.List;

/**
 * 菜单执行器
 * @author: yxkong
 * @date: 2023/12/27 11:09
 * @version: 1.0
 */
public interface IMenuExecutor {
    /**
     * 获取路由信息
     * @return 路由信息
     */
    List<RouterDto> getRouters();
    /**
     * 获取菜单下拉列表
     *
     * @return
     */
    List<TreeSelectDto> menuTree();
    /**
     * 授权菜单
     * @return
     */
    List<TreeSelectDto> authMenuTree();

    /**
     * 新增
     * @param context
     */
    void insert(SysMenuContext context);

    /**
     * 重新加载
     */
    void reloadPermission();

    /**
     * 查询菜单
     * @param context
     * @return
     */
    List<SysMenuDto> query(SysMenuQueryContext context);

    /**
     * 更新菜单
     * @param context
     */
    void update(SysMenuContext context);

    /**
     * 删除菜单
     * @param id
     */
    void delete(Long id);

    /**
     * 查找菜单
     * @param id
     * @return
     */
    SysMenuDto findById(Long id);
}
