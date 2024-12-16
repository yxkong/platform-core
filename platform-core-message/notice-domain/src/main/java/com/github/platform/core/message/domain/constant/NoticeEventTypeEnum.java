package com.github.platform.core.message.domain.constant;

import lombok.Getter;

/**
 * 项目流转通知
 * @Author: yxkong
 * @Date: 2024/12/12
 * @version: 1.0
 */
@Getter
public enum NoticeEventTypeEnum {
    PM_FLOW_NOTICE_EVENT("pmFlowNoticeEvent", "项目流转通知事件"),
    ;


    private String type;
    private String desc;

    NoticeEventTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
