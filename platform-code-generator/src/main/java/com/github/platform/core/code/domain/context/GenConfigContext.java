package com.github.platform.core.code.domain.context;

import com.github.platform.core.code.domain.common.entity.CodeGenConfigBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 代码生成配置上下文
 * @Author: yxkong
 * @Date: 2023/4/28 3:34 PM
 * @version: 1.0
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GenConfigContext extends CodeGenConfigBase {
}
