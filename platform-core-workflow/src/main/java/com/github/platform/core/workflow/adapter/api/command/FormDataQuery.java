package com.github.platform.core.workflow.adapter.api.command;
import com.github.platform.core.workflow.domain.common.query.FormDataQueryBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
* 表单数据查询
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:23.341
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "表单数据查询")
public class FormDataQuery extends FormDataQueryBase {
}