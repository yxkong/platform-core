package com.github.platform.core.code.domain.context;

import com.github.platform.core.code.domain.common.query.CodeColumnConfigQueryBase;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 表查询上下文
 * @Author: yxkong
 * @Date: 2023/4/25 3:12 PM
 * @version: 1.0
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TableQueryContext extends CodeColumnConfigQueryBase {
}
