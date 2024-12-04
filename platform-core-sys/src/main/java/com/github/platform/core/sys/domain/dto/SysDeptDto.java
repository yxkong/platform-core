package com.github.platform.core.sys.domain.dto;

import com.github.platform.core.sys.domain.common.entity.SysDeptBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
* 系统部门传输实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.241
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Schema(description = "系统部门传输实体")
public class SysDeptDto extends SysDeptBase{
    /**
     * 父部门名称
     */
    @Schema(description ="父部门名称")
    private String parentName;
    /**
     * 子部门
     */
    @Schema(description ="子部门")
    private List<SysDeptDto> children = new ArrayList<>();
}