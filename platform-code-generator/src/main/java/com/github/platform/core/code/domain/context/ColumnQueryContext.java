package com.github.platform.core.code.domain.context;

import com.github.platform.core.code.domain.common.query.CodeColumnConfigQueryBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 字段查询上下文
 * @Author: yxkong
 * @Date: 2023/4/25 3:58 PM
 * @version: 1.0
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ColumnQueryContext extends CodeColumnConfigQueryBase {
}
