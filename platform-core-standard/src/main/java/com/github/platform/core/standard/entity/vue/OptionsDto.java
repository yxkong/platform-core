package com.github.platform.core.standard.entity.vue;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * vue下拉通用结构
 * @author: yxkong
 * @date: 2023/5/17 10:31 AM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionsDto implements Serializable {
    /** 下拉value */
    @Schema(description = "下拉value")
    private Object value;
    /** 所属系统/模块或分类 */
    @Schema(description = "所属系统/模块")
    private String module;
    /** 下拉显示 */
    @Schema(description = "下拉显示")
    private String label;
    @Schema(description = "子选项")
    private List<OptionsDto> children;
    public OptionsDto(Object value, String label) {
        this.value = value;
        this.label = label;
    }
    public OptionsDto(Object value,String module, String label) {
        this.value = value;
        this.module = module;
        this.label = label;
    }
    public OptionsDto(Object value, String label,List<OptionsDto> children) {
        this.value = value;
        this.label = label;
        this.children = children;
    }

}
