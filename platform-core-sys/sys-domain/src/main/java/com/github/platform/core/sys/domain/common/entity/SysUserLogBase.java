package com.github.platform.core.sys.domain.common.entity;

import com.github.platform.core.common.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
* 用户日志表模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.892
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SysUserLogBase extends BaseAdminEntity   {
    /** 登录名 */
    @Schema(description = "登录名")
    @NotEmpty(message="登录名（loginName）不能为空")
    protected String loginName;
    /** 业务类型，0，注册，1,登录，2，改密，3注销，4，绑定账户 */
    @Schema(description = "业务类型，0，注册，1,登录，2，改密，3注销，4，绑定账户")
    @NotNull(message="业务类型，0，注册，1,登录，2，改密，3注销，4，绑定账户（bizType）不能为空")
    protected Integer bizType;
    /** 注册ip */
    @Schema(description = "注册ip")
    @NotEmpty(message="注册ip（requestIp）不能为空")
    protected String requestIp;
}
