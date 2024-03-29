package com.github.platform.core.code.domain.context;

import com.github.platform.core.code.domain.common.entity.CodeColumnConfigBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 字段上下文
 * @Author: yxkong
 * @Date: 2023/4/28 3:35 PM
 * @version: 1.0
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ColumnContext extends CodeColumnConfigBase {
}
