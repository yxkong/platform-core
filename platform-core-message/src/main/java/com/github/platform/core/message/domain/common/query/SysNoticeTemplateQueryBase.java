package com.github.platform.core.message.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
/**
 * 消息通知模板查询基类
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-10-12 15:28:38.800
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "消息通知模板查询")
public class SysNoticeTemplateQueryBase extends PageQueryBaseEntity {
    /** 模板标号 */
    @Schema(description = "模板标号")
    private String tempNo;
    /** 通知方式，,email,钉钉 */
    @Schema(description = "通知方式，,email,钉钉")
    private String noticeType;
    /** 业务模块，区分这个通知用于哪个业务，,pm、workflow */
    @Schema(description = "业务模块，区分这个通知用于哪个业务，,pm、workflow")
    private String bizModule;
    /** 用于消费指定类型的事件 */
    @Schema(description = "用于消费指定类型的事件")
    private String eventName;
    /** 通知标题 */
    @Schema(description = "通知标题")
    private String title;
}