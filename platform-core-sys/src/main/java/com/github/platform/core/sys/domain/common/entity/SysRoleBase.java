package com.github.platform.core.sys.domain.common.entity;

import com.github.platform.core.common.entity.BaseAdminEntity;
import com.github.platform.core.standard.validate.Add;
import com.github.platform.core.standard.validate.Modify;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
* 系统角色模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.077
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
public class SysRoleBase extends BaseAdminEntity   {
    /** 角色名称 */
    @NotBlank(message = "角色名称不能为空", groups = {Add.class, Modify.class})
    @Schema(description ="角色名称")
    private String name;
    /** 角色权限字符串 */
    @NotBlank(message = "角色标识不能为空", groups = {Add.class,Modify.class})
    @Schema(description = "角色权限字符串")
    protected String key;
    /** 显示顺序 */
    @Schema(description = "显示顺序")
    protected Integer sort;
    /** 数据范围（） */
    @Schema(description = "数据范围（）")
    protected String dataScope;
}
