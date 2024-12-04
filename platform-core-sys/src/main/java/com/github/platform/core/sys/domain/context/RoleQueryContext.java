package com.github.platform.core.sys.domain.context;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class RoleQueryContext extends PageQueryBaseEntity {
    /**
     * 角色名称
     */
    private String name;
}
