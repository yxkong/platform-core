package com.github.platform.core.sys.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import com.github.platform.core.standard.validate.Other;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
* 用户信息查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.651
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "用户信息查询")
public class SysUserQueryBase extends PageQueryBaseEntity {
    /**
     * 用户姓名
     */
    @Schema(description ="用户姓名")
    private String userName;
    /**
     * 登录账户
     */
    @Schema(description ="登录账户")
    private String loginName;

    /**
     * 手机号
     */
    @Schema(description ="手机号")
    private String mobile;

    @Schema(description ="角色id")
    private Long roleId;
    @Schema(description ="角色key")
    private String roleKey;

    /**
     * 部门ID
     */
    @Schema(description ="部门ID")
    private Long deptId;
}