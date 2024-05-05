package com.github.platform.core.sys.domain.common.entity;

import com.github.platform.core.common.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 用户配置模型实体
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-03-27 16:10:38.663
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class SysUserConfigBase extends BaseAdminEntity {
    @NotEmpty(message="登录名称（loginName）不能为空")
    /** 登录名称 */
    @Schema(description = "登录名称")
    protected String loginName;
    @NotEmpty(message="参数键名（configKey）不能为空")
    /** 参数键名 */
    @Schema(description = "参数键名")
    protected String configKey;
    @NotEmpty(message="参数键值（value）不能为空")
    /** 参数键值 */
    @Schema(description = "参数键值")
    protected String value;
    @NotEmpty(message="值类型：int,str,json（valType）不能为空")
    /** 值类型：int,str,json */
    @Schema(description = "值类型：int,str,json")
    protected String valType;
    @NotNull(message="状态（1正常 0停用）（status）不能为空")
    /** 状态（1正常 0停用） */
    @Schema(description = "状态（1正常 0停用）")
    protected Integer status;
    /** 创建人(数据归属人)，存储登录名 */
    @Schema(description = "创建人(数据归属人)，存储登录名")
    protected String createBy;
    /** 更新人，存储登录名 */
    @Schema(description = "更新人，存储登录名")
    protected String updateBy;
}
