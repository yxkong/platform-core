package com.github.platform.core.sms.domain.dto;

import com.github.platform.core.sms.domain.common.entity.SysSmsTemplateStatusBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 模板厂商传输实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.326
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "模板厂商传输实体")
public class SysSmsTemplateStatusDto extends SysSmsTemplateStatusBase {
}