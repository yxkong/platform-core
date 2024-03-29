package com.github.platform.core.sys.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
/**
* 登录日志查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.685
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "登录日志查询")
public class SysLoginLogQueryBase extends PageQueryBaseEntity {
    /** 登录方式 */
    @Schema(description = "登录方式")
    private String loginWay;
}