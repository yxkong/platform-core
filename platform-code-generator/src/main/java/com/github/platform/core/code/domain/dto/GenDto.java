package com.github.platform.core.code.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 代码生成配置实体
 * @Author: yxkong
 * @Date: 2023/4/28 4:48 PM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenDto {
    private Long id;
    private String tableName;
    private GenConfigDto genConfig;
    private List<ColumnDto> columns;
}
