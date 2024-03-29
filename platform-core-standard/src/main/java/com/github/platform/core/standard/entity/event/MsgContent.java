package com.github.platform.core.standard.entity.event;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 事件包装体，兼容历史业务，不要轻易改动
 * @author: yxkong
 * @date: 2021/4/5 8:28 下午
 * @version: 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MsgContent<T> implements Serializable {
    private static final long serialVersionUID = -5115473473963556887L;

    /**
     * 来源的项目名称,使用驼峰，英文
     */
    @Schema(description = "来源的项目名称,使用驼峰，英文")
    protected String serviceName;

    /**
     * 消息所属的业务模块，例如：用户
     */
    private String msgModule;
    /**
     * 消息节点，比如用户：login,register 等
     */
    @Schema(description = "消息节点")
    protected String msgNode;
    /**
     * 消息发送的时间戳
     */
    @Schema(description = "消息发送的时间戳")
    protected Long sendTime;
    /**
     * 消息唯一标识，如果没有data进行md5
     */
    @Schema(description = "消息唯一标识，如果没有data进行md5")
    protected String msgId;

    /**
     * 消息体
     */
    @Schema(description = "消息体")
    protected T data;
    /**
     * 消息状态，1成功，0，失败
     */
    @Schema(description = "消息状态，1成功，0，失败")
    private Integer status;

    /**
     * 用户操作来源
     */
    private String proId;
    /**用户唯一标识*/
    private Long userId;

    /**
     * 手机号
     */
    private String mobile;
    /** 租户号*/
    private Integer tenantId;

    /**
     * 信息
     */
    private String msg;

}