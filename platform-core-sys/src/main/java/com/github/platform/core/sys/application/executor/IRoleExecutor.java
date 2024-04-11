package com.github.platform.core.sys.application.executor;

import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.sys.domain.context.SysRoleContext;
import com.github.platform.core.sys.domain.context.SysRoleQueryContext;
import com.github.platform.core.sys.domain.dto.SysRoleDto;
import com.github.platform.core.sys.domain.dto.resp.RoleDetailDto;

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

    void addRole(SysRoleContext roleContext);

    void deleteById(Long roleId);

    void editRole(SysRoleContext roleContext);

    RoleDetailDto queryDetail(Long id);
}
