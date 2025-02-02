package com.github.platform.core.schedule.domain.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.common.entity.BaseAdminEntity;
import com.github.platform.core.schedule.domain.constant.JobStatusEnum;
import com.github.platform.core.schedule.domain.constant.JobTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
* 任务管理模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-05 11:37:59.627
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
public class SysJobBase extends BaseAdminEntity   {
    /** 任务名称 */
    @Schema(description = "任务名称")
    @NotEmpty(message="任务名称（name）不能为空")
    protected String name;
    /** 处理器bean名称 */
    @Schema(description = "处理器bean名称")
    protected String beanName;
    @Schema(description = "任务类型：1本地单实例，0远程，2多实例")
    @NotNull(message = "任务类型（jobType）不能为空")
    protected Integer jobType;
    /** 处理器参数 */
    @Schema(description = "处理器参数")
    protected String handlerParam;
    /**回调url*/
    @Schema(description = "回调url")
    protected String callBackUrl;
    /**访问token*/
    @Schema(description = "访问token")
    protected String accessToken;
    /** 定时任务表达式 */
    @Schema(description = "定时任务表达式")
    protected String cronExpression;
    /** 开始时间,null 用于判断 */
    @Schema(description = "开始时间,null 用于判断")
    protected LocalDateTime startDate;
    /** 结束时间，null 用于判断 */
    @Schema(description = "结束时间，null 用于判断")
    protected LocalDateTime endDate;
    /** 重试次数，0为不重试 */
    @Schema(description = "重试次数，0为不重试")
    protected Integer retryCount;
    /** 重试间隔，单位为秒，0为直接重试 */
    @Schema(description = "重试间隔，单位为秒，0为直接重试")
    protected Integer retryInterval;
    /** 监控超时时间,最大监控时间，单位秒 */
    @Schema(description = "监控超时时间,最大监控时间，单位秒")
    protected Integer monitorTimeout;
    /**任务状态*/
    @Schema(description = "任务状态")
    protected Integer jobStatus;
    /**子任务id，以逗号,分隔*/
    @Schema(description = "子任务id，以逗号,分隔")
    protected String subJobIds;

    /**
     * 是否回调
     * @return
     */
    @JsonIgnore
    public boolean isCallBack(){
        return Objects.equals(JobTypeEnum.CALLBACK.getType(),this.jobType);
    }

    @JsonIgnore
    public boolean isEnable(){
        return JobStatusEnum.NORMAL.getStatus().equals(this.status);
    }
    /**
     * 是否多实例
     * @return
     */
    @JsonIgnore
    public boolean isMultiInstance(){
        return this.isCallBack() || Objects.equals(JobTypeEnum.MULTI_INSTANCE.getType(),this.jobType)  ;
    }
}
