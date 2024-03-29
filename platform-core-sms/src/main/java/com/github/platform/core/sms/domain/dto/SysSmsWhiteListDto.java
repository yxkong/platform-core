package com.github.platform.core.sms.domain.dto;

import com.github.platform.core.sms.domain.common.entity.SysSmsWhiteListBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 短信白名单传输实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.643
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "短信白名单传输实体")
public class SysSmsWhiteListDto extends SysSmsWhiteListBase {
}