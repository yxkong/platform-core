package com.github.platform.core.sys.domain.context;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class RoleQueryContext extends PageQueryBaseEntity {
    /**
     * 角色名称
     */
    private String name;
}
