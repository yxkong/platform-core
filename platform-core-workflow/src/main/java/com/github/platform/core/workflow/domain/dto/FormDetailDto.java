package com.github.platform.core.workflow.domain.dto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
* 表单配置传输实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:18.130
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@Schema(description = "表单配置传输实体")
public class FormDetailDto{
    FormDto basic;
    List<FormInfoDto> infos;
}