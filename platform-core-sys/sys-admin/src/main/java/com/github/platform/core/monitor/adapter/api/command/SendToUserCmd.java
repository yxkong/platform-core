package com.github.platform.core.monitor.adapter.api.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
 * 在线用户发送消息实体
 * @author: yxkong
 * @date: 2023/6/28 3:50 PM
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "在线用户发送消息实体")
public class SendToUserCmd {
    @Schema(description = "业务类型，wsLogout 强制退出,wsChat")
    private String bizType;
    @Schema(description = "从哪个用户")
    private String fromUser;
    @Schema(description = "指定用户,当为all的时候是所有")
    private String toUser;
    @Schema(description = "消息文本")
    private String text;
}
