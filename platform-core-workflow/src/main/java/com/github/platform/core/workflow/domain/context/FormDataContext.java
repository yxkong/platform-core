package com.github.platform.core.workflow.domain.context;
import com.github.platform.core.workflow.domain.common.entity.FormDataBase;
import lombok.experimental.SuperBuilder;
import lombok.*;
/**
* 表单数据增加或修改上下文
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
public class FormDataContext extends FormDataBase {
}
