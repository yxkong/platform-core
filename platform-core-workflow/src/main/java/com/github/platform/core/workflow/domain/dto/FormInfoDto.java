package com.github.platform.core.workflow.domain.dto;

import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.workflow.domain.common.entity.FormInfoBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
* 表单信息传输实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:21.269
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "表单信息传输实体")
public class FormInfoDto extends FormInfoBase{
    /**
     * 配置项
     */
    private List<OptionsDto> options;

    /**表单数据值*/
    private String value;
    /**是否禁用*/
    private boolean disabled;
}