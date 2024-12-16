package com.github.platform.core.monitor.domain.ws;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.monitor.domain.constant.WsConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * websocket消息体
 * @author: yxkong
 * @date: 2023/6/12 4:53 PM
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "websocket消息体")
public class OutMessage<T> implements Serializable {
    @Schema(description = "业务类型，wsLogout 强制退出,wsChat")
    private String bizType;
    @Schema(description = "消息状态")
    private String status;
    @Schema(description = "消息提示内容")
    private String message;
    @Schema(description = "从哪个用户")
    private String fromUser;
    @Schema(description = "指定用户,当为")
    private String toUser;
    @Schema(description = "发送时间")
    private LocalDateTime sendTime;
    @Schema(description = "消息体")
    private T data;

    @JsonIgnore
    public boolean isValidBizType(){
        if (WsConstant.BIZ_TYPE_CHAT.equals(bizType) || WsConstant.BIZ_TYPE_LOGOUT.equals(bizType)){
            return true;
        }
        return false;
    }
    @JsonIgnore
    public boolean isLogout(){
        return  WsConstant.BIZ_TYPE_LOGOUT.equals(bizType);
    }
    @JsonIgnore
    public boolean isChat(){
        return WsConstant.BIZ_TYPE_CHAT.equals(bizType);
    }
}
