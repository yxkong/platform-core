package com.github.platform.core.workflow.domain.context;
import com.github.platform.core.workflow.domain.common.entity.FormBase;
import lombok.experimental.SuperBuilder;
import lombok.*;

import java.util.List;

/**
* 表单配置增加或修改上下文
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:18.130
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FormContext extends FormBase {
}
