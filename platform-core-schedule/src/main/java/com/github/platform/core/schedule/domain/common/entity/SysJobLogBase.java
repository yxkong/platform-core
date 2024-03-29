package com.github.platform.core.schedule.domain.common.entity;
import com.github.platform.core.common.entity.BaseAdminEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
* 任务执行日志模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-06 18:53:10.711
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
public class SysJobLogBase extends BaseAdminEntity {
    /** 任务id */
    @Schema(description = "任务id")
    protected Long jobId;
    /** 处理器bean名称 */
    @Schema(description = "处理器bean名称")
    protected String beanName;
    /** 执行id，异常重试的时候，保持一致 */
    @Schema(description = "执行id，异常重试的时候，保持一致 ")
    protected String executeId;
    /** 处理器参数 */
    @Schema(description = "处理器参数")
    @NotEmpty(message="处理器参数（handlerParam）不能为空")
    protected String handlerParam;
    /** 开始执行时间 */
    @Schema(description = "开始执行时间")
    @NotNull(message="开始执行时间（startTime）不能为空")
    protected LocalDateTime startTime;
    /** 结束执行时间 */
    @Schema(description = "结束执行时间")
    @NotNull(message="结束执行时间（endDate）不能为空")
    protected LocalDateTime endDate;
    /** 第n次执行，默认为0 */
    @Schema(description = "第n次执行，默认为0")
    @NotNull(message="第n次执行，默认为0（executeNum）不能为空")
    protected Integer executeNum;
    /** 执行耗时，毫秒 */
    @Schema(description = "执行耗时，毫秒")
    @NotNull(message="执行耗时，毫秒（executeTime）不能为空")
    protected Integer executeTime;
    /** 执行结果 */
    @Schema(description = "执行结果")
    @NotEmpty(message="执行结果（result）不能为空")
    protected String result;
}
