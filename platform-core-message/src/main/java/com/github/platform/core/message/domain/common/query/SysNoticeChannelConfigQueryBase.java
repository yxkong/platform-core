package com.github.platform.core.message.domain.common.query;
import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
 * 通知通道配置查询基类
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
@Schema(description = "通知通道配置查询")
public class SysNoticeChannelConfigQueryBase extends PageQueryBaseEntity {
    /** 渠道类型，email、dingding、feishu */
    @Schema(description = "渠道类型，email、dingding、feishu")
    private String channelType;
}