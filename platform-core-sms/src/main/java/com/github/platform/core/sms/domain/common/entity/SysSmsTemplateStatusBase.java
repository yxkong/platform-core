package com.github.platform.core.sms.domain.common.entity;

import com.github.platform.core.common.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
* 模板厂商模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.326
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
public class SysSmsTemplateStatusBase extends BaseAdminEntity   {
    /** 模板编号 */
    @Schema(description = "模板编号")
    protected String tempNo;
    /** 厂商编号 */
    @Schema(description = "厂商编号")
    @NotEmpty(message="厂商编号（proNo）不能为空")
    protected String proNo;
    /** 签名状态：0，未申请，1申请成功，2，申请中  -1，申请失败 */
    @Schema(description = "签名状态：0，未申请，1申请成功，2，申请中  -1，申请失败")
    protected Integer signStatus;
    /** 签名申请三方消息id，用于回调以后查找数据 */
    @Schema(description = "签名申请三方消息id，用于回调以后查找数据")
    protected String signMsgId;
    /** 模板状态：0，未申请，1申请成功，2申请中  -1，申请失败 */
    @Schema(description = "模板状态：0，未申请，1申请成功，2申请中  -1，申请失败")
    protected Integer tempStatus;
    /** 服务商的模板id */
    @Schema(description = "服务商的模板id")
    @NotEmpty(message="服务商的模板id（tempId）不能为空")
    protected String tempId;
    /** 模板申请三方消息id */
    @Schema(description = "模板申请三方消息id")
    protected String tempMsgId;
    /** 排序 */
    @Schema(description = "排序")
    protected Integer sort;
    /** 添加类型：0，程序，1人工 */
    @Schema(description = "添加类型：0，程序，1人工")
    protected Integer type;
}
