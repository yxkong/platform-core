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
    PM_FLOW_NOTICE_EVENT("pmFlowNoticeEvent", "项目流转通知"),
    PM_NODE_OVERDUE_NOTICE_EVENT("pmNodeOverdueNoticeEvent", "节点任务超期未处理通知"),
    PM_NODE_PROCESS_NO_UPDATE_NOTICE_EVENT("pmNodeProgressNoticeEvent", "节点进度未更新通知"),
    PM_NODE_COMPLETED_SEND_EMAIL("pmNodeCompletedSendEmail", "节点完成发送邮件"),
    PM_NODE_TASK_DELIVERY_NOTICE_EVENT("pmNodeTaskDeliveryNoticeEvent", "已登记工时未填写交付物通知"),
    PM_DEFECT_NOTICE_EVENT("pmDefectNoticeEvent", "项目缺陷通知事件"),
    ;


    private String type;
    private String desc;

    NoticeEventTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
