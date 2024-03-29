package com.github.platform.core.workflow.adapter.api.command;

import com.github.platform.core.workflow.domain.common.query.ProcessQueryBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 流程查询
 * @author: yxkong
 * @date: 2023/11/13 14:59
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "流程查询")
public class ProcessQuery extends ProcessQueryBase {
}
