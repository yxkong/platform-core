package com.github.platform.core.code.domain.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 代码生成上下文
 * @Author: yxkong
 * @Date: 2023/4/28 3:26 PM
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GenContext {
   private GenConfigContext genConfig;
   private List<ColumnContext> columns;
}
