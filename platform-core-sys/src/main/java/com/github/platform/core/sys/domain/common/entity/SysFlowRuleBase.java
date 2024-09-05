package com.github.platform.core.sys.domain.common.entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.github.platform.core.common.entity.BaseAdminEntity;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 状态机配置规则模型实体
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-29 10:25:01.691
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class SysFlowRuleBase extends BaseAdminEntity   {
    /** 业务类型 */
    @Schema(description = "业务类型")
    protected String bizType;
    /** 当前状态 */
    @Schema(description = "当前状态")
    protected String currentStatus;
    /** 目标可选状态 */
    @Schema(description = "目标可选状态")
    protected String targetStatus;
    /**默认状态*/
    @Schema(description = "默认状态")
    protected String defaultTargetStatus;
}
