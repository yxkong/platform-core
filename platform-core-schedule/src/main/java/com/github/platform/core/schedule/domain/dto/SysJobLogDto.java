package com.github.platform.core.schedule.domain.dto;
import com.github.platform.core.schedule.domain.common.entity.SysJobLogBase;
import lombok.*;
import lombok.experimental.SuperBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
/**
* 任务执行日志传输实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-06 18:53:10.711
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "任务执行日志传输实体")
public class SysJobLogDto extends SysJobLogBase{
}