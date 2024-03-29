package com.github.platform.core.schedule.domain.dto;
import com.github.platform.core.schedule.domain.common.entity.SysJobBase;
import lombok.*;
import lombok.experimental.SuperBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
/**
* 任务管理传输实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-05 11:37:59.627
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "任务管理传输实体")
public class SysJobDto extends SysJobBase {
}