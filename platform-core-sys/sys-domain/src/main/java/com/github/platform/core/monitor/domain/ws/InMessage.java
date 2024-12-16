package com.github.platform.core.monitor.domain.ws;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.monitor.domain.constant.WsConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 消息接收体
 * @author: yxkong
 * @date: 2023/6/28 3:53 PM
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "websocket消息体")
public class InMessage {
    @Schema(description = "业务类型，wsLogout 强制退出,wsChat")
    private String bizType;
    @Schema(description = "从哪个用户")
    private String fromUser;
    @Schema(description = "指定用户,当为all的时候是所有")
    private String toUser;
    @Schema(description = "发送时间，后端设置")
    private LocalDateTime sendTime;
    @Schema(description = "消息文本")
    private String text;
    @Schema(description = "接收用户信息")
    private LoginUserInfo loginInfo;

    @JsonIgnore
    public void setLoginInfoStr(String loginInfo) {
        this.loginInfo = JsonUtils.fromJson(loginInfo, LoginUserInfo.class);;
    }

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
    @JsonIgnore
    public boolean isAll(){
        return WsConstant.TO_ALL.equals(toUser);
    }
}
