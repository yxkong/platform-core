package com.github.platform.core.standard.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通用发布事件的事件体
 * @author: yxkong
 * @date: 2023/9/8 11:33 AM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonPublishDto<T> implements Serializable {
    /** 来源的项目名称,使用驼峰，英文*/
    private String sourceService;
    /**目标项目名称,使用驼峰，英文*/
    private String targetService;
    /**消息所属的业务模块*/
    private String module;
    /**消息节点，比如用户：login,register 等*/
    private String node;
    /**事件处理器，最后事件由哪个处理器处理*/
    private String handlerBean;
    /**发送时间*/
    private LocalDateTime sendTime;
    /**业务数据*/
    private T data;
}
