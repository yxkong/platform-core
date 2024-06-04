package com.github.platform.core.sys.adapter.api.command.thirduser;

import com.github.platform.core.sys.domain.common.entity.SysThirdUserBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
* 三方用户增加或修改
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.505
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "三方用户增加或修改")
public class SysThirdUserCmd extends SysThirdUserBase{
    /** 加密用户id */
    @Schema(description = "加密用户id")
    @NotNull(message="加密用户id（strUserId）不能为空")
    protected String strUserId;
}
