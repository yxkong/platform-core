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
 * @date 2024-10-12 15:28:38.800
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class SysNoticeTemplateBase extends BaseAdminEntity   {
    /** 模板标号 */
    @Schema(description = "模板标号")
    protected String tempNo;
    /** 通知方式，,email,钉钉 */
    @Schema(description = "通知方式，,email,钉钉")
    protected String noticeType;
    /** 业务模块，区分这个通知用于哪个业务，,pm、workflow */
    @Schema(description = "业务模块，区分这个通知用于哪个业务，,pm、workflow")
    protected String bizModule;
    /** 用于消费指定类型的事件 */
    @Schema(description = "用于消费指定类型的事件")
    protected String eventName;
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
