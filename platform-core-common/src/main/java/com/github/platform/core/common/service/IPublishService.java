package com.github.platform.core.common.service;

import com.github.platform.core.standard.entity.dto.CommonPublishDto;

/**
 * 发布接口定义
 * @author: yxkong
 * @date: 2023/9/8 11:30 AM
 * @version: 1.0
 */
public interface IPublishService {

    /**
     * 发布接口
     * @param dto
     * @return
     */
    boolean publish(CommonPublishDto dto);
}
