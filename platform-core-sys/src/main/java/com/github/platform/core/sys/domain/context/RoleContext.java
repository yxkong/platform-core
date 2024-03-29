package com.github.platform.core.sys.domain.context;

import lombok.Data;

import java.util.Set;

@Data
public class RoleContext {

    private Long id;
    private String name;
    private String key;
    /**
     * 数据权限,默认本人
     */
    private String dataScope;

    /**
     * 角色菜单权限
     */
    private Set<Long> menuIds;
    private Integer sort;
    private String status;
}
