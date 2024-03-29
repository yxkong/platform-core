package com.github.platform.core.common.convert;

import com.github.platform.core.standard.entity.common.LoginInfo;
import com.github.platform.core.standard.entity.event.MsgContent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 事件转化
 * @author: yxkong
 * @date: 2023/1/4 10:07 AM
 * @version: 1.0
 */
@Mapper
public interface EventConvert {
    EventConvert me =  Mappers.getMapper(EventConvert.class);
    /**
     * 事件组装
     * @param loginInfo
     * @param serviceName
     * @param msgModule
     * @param msgNode
     * @param msgId
     * @param data
     * @param status
     * @param msg
     * @return
     */
    @Mappings({
            @Mapping(target = "userId", source = "loginInfo.id"),
    })
    MsgContent of(LoginInfo loginInfo,String serviceName, String msgModule, String msgNode, String msgId, Object data,Integer status, String msg);
}
