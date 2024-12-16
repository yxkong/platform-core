package com.github.platform.core.sys.domain.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 密码返回实体
 *
 * @author: yxkong
 * @date: 2022/12/30 9:22 AM
 * @version: 1.0
 */
@Schema(description = "密码返回实体")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PwdResult {
    @Schema(description ="用户登录名")
    private String loginName;

    @Schema(description ="重置后的密码")
    private String pwd;
    @Schema(description ="加密后的密码")
    private String md5Pwd;
}
