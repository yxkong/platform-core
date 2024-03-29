package com.github.platform.core.workflow.domain.context;

import com.github.platform.core.workflow.domain.common.query.ProcessQueryBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 流程查询上下文
 * @author: yxkong
 * @date: 2023/11/13 15:00
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = " 流程查询上下文")
public class ProcessQueryContext extends ProcessQueryBase {
}
