package com.github.platform.core.workflow.adapter.api.command;

import com.github.platform.core.sys.domain.common.query.SysUserQueryBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 流程用户查询
 * @author: yxkong
 * @date: 2023/10/30 13:51
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "流程用户信息查询")
public class FlwUserQuery extends SysUserQueryBase {
}
