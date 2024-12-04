package com.github.platform.core.message.adapter.api.command;
import com.github.platform.core.message.domain.common.entity.SysNoticeChannelConfigBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
 * 通知通道配置增加或修改
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:32:28.892
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "通知通道配置增加或修改")
public class SysNoticeChannelConfigCmd extends SysNoticeChannelConfigBase{
}
