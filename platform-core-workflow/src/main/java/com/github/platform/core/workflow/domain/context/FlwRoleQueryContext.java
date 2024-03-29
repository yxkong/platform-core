package com.github.platform.core.workflow.domain.context;

import com.github.platform.core.workflow.domain.common.query.FlwRoleQueryBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 流程角色查询上下文
 * @author: yxkong
 * @date: 2023/10/30 13:37
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FlwRoleQueryContext extends FlwRoleQueryBase {
}
