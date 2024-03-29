package com.github.platform.core.dingtalk.domain.dto;
import com.github.platform.core.dingtalk.domain.common.entity.DingDeptBase;
import lombok.*;
import lombok.experimental.SuperBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 钉钉部门传输实体
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-01-18 15:49:32.694
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "钉钉部门传输实体")
public class DingDeptDto extends DingDeptBase{
}