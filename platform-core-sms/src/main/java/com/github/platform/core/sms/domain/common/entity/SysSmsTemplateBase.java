package com.github.platform.core.sms.domain.common.entity;

import com.github.platform.core.standard.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
* 短信模板模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.124
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
public class SysSmsTemplateBase extends BaseAdminEntity   {
    /** 模板编号 */
    @Schema(description = "模板编号")
    protected String tempNo;
    /** 短信类型（业务类型） */
    @Schema(description = "短信类型（业务类型）")
    protected String smsType;
    /** 模板名称 */
    @Schema(description = "模板名称")
    @NotEmpty(message="模板名称（name）不能为空")
    protected String name;
    /** 短信签名 */
    @Schema(description = "短信签名")
    @NotEmpty(message="短信签名（signName）不能为空")
    protected String signName;
    /** 模板内容 */
    @Schema(description = "模板内容")
    @NotEmpty(message="模板内容（content）不能为空")
    protected String content;
    /** 路由类型 */
    @Schema(description = "路由类型")
    protected String routeType;
    /** 厂商编码 */
    @Schema(description = "厂商编码")
    protected String proNo;
    /** 短信类型：verify/notice/market */
    @Schema(description = "短信类型：verify/notice/market")
    @NotEmpty(message="短信类型：verify/notice/market（type）不能为空")
    protected String type;
    /** 可用时间类型：0，无限制，1自定义 */
    @Schema(description = "可用时间类型：0，无限制，1自定义")
    protected Integer useTimeType;
    /** 工作时间区间 00:00:00 - 23:59:59 */
    @Schema(description = "工作时间区间 00:00:00 - 23:59:59")
    protected String workInterval;
    /** 节假日时间区间 00:00:00 - 23:59:59 */
    @Schema(description = "节假日时间区间 00:00:00 - 23:59:59")
    protected String holidayInterval;
    /** 申请模板的公司名称 */
    @Schema(description = "申请模板的公司名称")
    protected String applyName;
    /** 申请用的公司网址 */
    @Schema(description = "申请用的公司网址")
    protected String applyUrl;
    /** 申请原因说明 */
    @Schema(description = "申请原因说明")
    protected String applyReason;
}
