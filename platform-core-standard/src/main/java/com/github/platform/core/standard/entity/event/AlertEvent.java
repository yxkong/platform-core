package com.github.platform.core.standard.entity.event;

import com.github.platform.core.standard.constant.ResultStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 告警事件
 * @author: yxkong
 * @date: 2022/7/2 2:12 PM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertEvent<T> implements Serializable {
    /**事件来源（位置）*/
    private String source;
    /**事件级别，0（error），1(warn)，2(info)*/
    private AlterLevelEnum level;
    /**事件唯一标识符*/
    private String msgId;
    /**事件产生时间*/
    private LocalDateTime createTime = LocalDateTime.now();
    /**业务状态：1,成功，0失败*/
    private ResultStatusEnum status;
    /**事件数据*/
    private  T data;


}
