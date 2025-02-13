package com.github.platform.core.sys.domain.dto;

import com.github.platform.core.sys.domain.common.entity.SysUserBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
* 用户信息传输实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.651
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Schema(description = "用户信息传输实体")
public class SysUserDto extends SysUserBase{
    @Schema(description ="部门名称")
    private String deptName;

    /**
     * 用户角色列表
     */
    @Schema(description ="用户角色列表")
    private List<String> roleKeys;
    @Schema(description ="用户角色名称列表")
    private String roleNameStr;
}