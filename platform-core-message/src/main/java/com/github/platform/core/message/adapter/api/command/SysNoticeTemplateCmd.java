package com.github.platform.core.message.adapter.api.command;
import com.github.platform.core.message.domain.common.entity.SysNoticeTemplateBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
 * 消息通知模板增加或修改
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:32:24.593
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "消息通知模板增加或修改")
public class SysNoticeTemplateCmd extends SysNoticeTemplateBase{
}
