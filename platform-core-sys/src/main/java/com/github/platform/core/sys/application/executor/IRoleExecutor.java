package com.github.platform.core.sys.application.executor;

import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.sys.domain.context.SysRoleContext;
import com.github.platform.core.sys.domain.context.SysRoleQueryContext;
import com.github.platform.core.sys.domain.dto.SysRoleDto;

import java.util.List;

/**
 * 角色执行器
 * @author: yxkong
 * @date: 2023/12/27 11:17
 * @version: 1.0
 */
public interface IRoleExecutor {

    /**
     * 角色列表查询
     * @param context
     * @return
     */
    PageBean<SysRoleDto> query(SysRoleQueryContext context);

    /**
     * 角色下拉框
     * @param context
     * @return
     */
    List<OptionsDto> select(SysRoleQueryContext context);

    /**
     * 添加角色
     * @param roleContext
     */
    void addRole(SysRoleContext roleContext);

    /**
     * 删除角色
     * @param roleId
     */
    void deleteById(Long roleId);

    /**
     * 修改角色
     * @param roleContext
     */
    void update(SysRoleContext roleContext);

    /**
     * 查询角色详情
     * @param id
     * @return
     */
    SysRoleDto findById(Long id);
}
