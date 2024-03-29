package com.github.platform.core.workflow.adapter.api.command;

import com.github.platform.core.workflow.domain.common.query.FlwRoleQueryBase;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 工作流角色查询
 * @author: yxkong
 * @date: 2023/10/30 10:35
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel("流程角色查询")
public class FlwRoleQuery extends FlwRoleQueryBase {

}
