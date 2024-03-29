package com.github.platform.core.workflow.adapter.api.command;

import com.github.platform.core.workflow.domain.common.entity.FormInfoBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 *  表单明细信息
 * @author: yxkong
 * @date: 2023/11/27 17:33
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FormInfoCmd extends FormInfoBase {
}
