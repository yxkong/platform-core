package com.github.platform.core.sms.domain.common.entity;

import com.github.platform.core.common.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
* 服务商模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:29.748
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SysSmsServiceProviderBase extends BaseAdminEntity   {
    /** 厂商编号 */
    @Schema(description = "厂商编号")
    protected String proNo;
    /** 厂商简称 */
    @Schema(description = "厂商简称")
    @NotEmpty(message="厂商简称（proAlias）不能为空")
    protected String proAlias;
    /** 厂商名称 */
    @Schema(description = "厂商名称")
    @NotEmpty(message="厂商名称（proName）不能为空")
    protected String proName;
    /** 厂商服务的bean名称 */
    @Schema(description = "厂商服务的bean名称")
    @NotEmpty(message="厂商服务的bean名称（beanName）不能为空")
    protected String beanName;
    /** 厂商接口url */
    @Schema(description = "厂商接口url")
    @NotEmpty(message="厂商接口url（interfaceUrl）不能为空")
    protected String interfaceUrl;
    /** 厂商分配的账户 */
    @Schema(description = "厂商分配的账户")
    protected String account;
    /** 加密密码 */
    @Schema(description = "加密密码")
    protected String encryptPwd;
    /** 加密盐值 */
    @Schema(description = "加密盐值")
    protected String salt;
    /** 可用条数 */
    @Schema(description = "可用条数")
    protected Integer usableNum;
    /** 报警阈值 */
    @Schema(description = "报警阈值")
    protected Integer alarmThreshold;
    /** 排序 */
    @Schema(description = "排序")
    protected Integer sort;
}
