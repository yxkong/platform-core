package com.github.platform.core.message.domain.common.entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.github.platform.core.common.entity.BaseAdminEntity;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 消息通知模板模型实体
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:32:24.593
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class SysNoticeTemplateBase extends BaseAdminEntity   {
    /** 模板id,唯一编码 */
    @Schema(description = "模板id,唯一编码")
    protected String tempNo;
    /** 默认通道 */
    @Schema(description = "默认通道")
    protected String channelType;
    /** 事件类型 */
    @Schema(description = "事件类型")
    @NotEmpty(message="事件类型（eventType）不能为空")
    protected String eventType;
    /** 消息类型：text、markdown */
    @Schema(description = "消息类型：text、markdown")
    @NotEmpty(message="消息类型：text、markdown（msgType）不能为空")
    protected String msgType;
    /** 通知标题 */
    @Schema(description = "通知标题")
    protected String title;
    /** 消息模板内容 */
    @Schema(description = "消息模板内容")
    @NotEmpty(message="消息模板内容（context）不能为空")
    protected String context;
    /** 接收人 */
    @Schema(description = "接收人")
    protected String recipient;
    /** 抄送人，邮件专用 */
    @Schema(description = "抄送人，邮件专用")
    protected String carbonCopy;
}
