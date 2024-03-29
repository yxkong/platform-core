package com.github.platform.core.common.event;

import com.github.platform.core.standard.entity.dto.CommonPublishDto;
import org.springframework.context.ApplicationEvent;

/**
 * CommonPublishDto的evet包装
 * @author: yxkong
 * @date: 2023/9/8 12:27 PM
 * @version: 1.0
 */
public class CommonPublishEvent extends ApplicationEvent {

    private CommonPublishDto commonPublishDto;

    public CommonPublishEvent(Object source, CommonPublishDto commonPublishDto) {
        super(source);
        this.commonPublishDto = commonPublishDto;
    }
    public CommonPublishDto getCommonPublishDto() {
        return commonPublishDto;
    }
}
