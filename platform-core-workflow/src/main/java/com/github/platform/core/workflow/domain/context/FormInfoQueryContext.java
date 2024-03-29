package com.github.platform.core.workflow.domain.context;

import com.github.platform.core.workflow.domain.common.query.FormInfoQueryBase;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
* 表单信息查询上下文
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
public class FormInfoQueryContext extends FormInfoQueryBase {
}
