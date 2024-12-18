package com.github.platform.core.message.domain.common.entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.github.platform.core.common.entity.BaseAdminEntity;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 通知事件日志模型实体
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:36:01.514
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class SysNoticeEventLogBase extends BaseAdminEntity   {
    /** 消息唯一id */
    @Schema(description = "消息唯一id")
    @NotEmpty(message="消息唯一id（msgId）不能为空")
    protected String msgId;
    /** 事件类型 */
    @Schema(description = "事件类型")
    @NotEmpty(message="事件类型（eventType）不能为空")
    protected String eventType;
    /** 时间所属模块 */
    @Schema(description = "时间所属模块")
    @NotEmpty(message="时间所属模块（module）不能为空")
    protected String module;
    /** 事件所处节点 */
    @Schema(description = "事件所处节点")
    @NotEmpty(message="事件所处节点（node）不能为空")
    protected String node;
    /** 事件内容 */
    @Schema(description = "事件内容")
    @NotEmpty(message="事件内容（payload）不能为空")
    protected String payload;
}
