package com.github.platform.core.monitor.adapter.api.convert;

import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.monitor.adapter.api.command.SendToUserCmd;
import com.github.platform.core.monitor.domain.dto.OnLineUserDto;
import com.github.platform.core.monitor.domain.ws.InMessage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 在线用户发送消息转换
 *
 * @author: yxkong
 * @date: 2023/6/8 6:45 PM
 * @version: 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OnLineUserConvert {
    OnLineUserDto toDto(LoginUserInfo userInfo,String lastTime);
    InMessage toInMsg(SendToUserCmd sendToUserCmd);
}
