package com.github.platform.core.common.service;

import com.github.platform.core.standard.entity.dto.CommonPublishDto;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 事件处理接口
 * @author: yxkong
 * @date: 2023/9/8 2:34 PM
 * @version: 1.0
 */
public interface IEventHandlerService<T> {
    /**
     * 处理器
     * @param dto
     * @return
     */
    Pair<Boolean,String> handler(CommonPublishDto<T> dto);
}
