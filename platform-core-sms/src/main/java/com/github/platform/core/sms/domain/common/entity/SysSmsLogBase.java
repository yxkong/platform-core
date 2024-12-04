package com.github.platform.core.sms.domain.common.entity;

import com.github.platform.core.common.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
* 短信日志模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.502
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SysSmsLogBase extends BaseAdminEntity   {
    /** 厂商编号，用于统计花费 */
    @Schema(description = "厂商编号，用于统计花费")
    @NotEmpty(message="厂商编号，用于统计花费（proNo）不能为空")
    protected String proNo;
    /** 手机号 */
    @Schema(description = "手机号")
    @NotEmpty(message="手机号（mobile）不能为空")
    protected String mobile;
    /** 消息唯一流水号 */
    @Schema(description = "消息唯一流水号")
    protected String msgId;
    /** 三方消息id */
    @Schema(description = "三方消息id")
    @NotEmpty(message="三方消息id（thridMsgId）不能为空")
    protected String thirdMsgId;
    /** 模板编号 */
    @Schema(description = "模板编号")
    protected String tempNo;
    /** 短信内容 */
    @Schema(description = "短信内容")
    protected String content;
    /** 回调回来的状态，0默认，-1发送失败，1发送成功 */
    @Schema(description = "回调回来的状态，0默认，-1发送失败，1发送成功")
    @NotNull(message="回调回来的状态，0默认，-1发送失败，1发送成功（sendStatus）不能为空")
    protected Integer sendStatus;
}
