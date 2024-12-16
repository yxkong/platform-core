package com.github.platform.core.message.domain.common.entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.github.platform.core.common.entity.BaseAdminEntity;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 通知通道配置模型实体
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:32:28.892
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class SysNoticeChannelConfigBase extends BaseAdminEntity   {
    /** 渠道类型，email、dingding、feishu */
    @Schema(description = "渠道类型，email、dingding、feishu")
    @NotEmpty(message="渠道类型，email、dingding、feishu（channelType）不能为空")
    protected String channelType;
    /** appKey或者appId */
    @Schema(description = "账户或key")
    protected String appKey;
    /** app应用密钥 */
    @Schema(description = "密码或密钥")
    protected String appSecret;
    /** 地址和 */
    @Schema(description = "地址")
    protected String host;
    /** 缓存前缀 */
    @Schema(description = "缓存前缀")
    protected String tokenKey;
    /** 渠道非标配置信息，JSON格式（如API地址、认证信息等） */
    @Schema(description = "渠道非标配置信息，JSON格式（如API地址、认证信息等）")
    @NotEmpty(message="渠道非标配置信息，JSON格式（如API地址、认证信息等）（config）不能为空")
    protected String config;
}
