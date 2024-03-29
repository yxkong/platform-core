package com.github.platform.core.schedule.domain.common.query;
import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
    import java.time.LocalDateTime;
/**
* 任务执行日志查询基类
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
@Schema(description = "任务执行日志查询")
public class SysJobLogQueryBase extends PageQueryBaseEntity {
    /** 任务id */
    @Getter
    @Schema(description = "任务id")
    protected Long jobId;
    /** 处理器bean名称 */
    @Schema(description = "处理器bean名称")
    protected String beanName;
    /** 执行id */
    @Schema(description = "执行id")
    protected String executeId;

}