package com.github.platform.core.sys.adapter.api.command;
import com.github.platform.core.sys.domain.common.query.SysFlowRuleQueryBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
 * 状态机配置规则查询
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-29 10:25:01.691
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "状态机配置规则查询")
public class SysFlowRuleQuery extends SysFlowRuleQueryBase {
}