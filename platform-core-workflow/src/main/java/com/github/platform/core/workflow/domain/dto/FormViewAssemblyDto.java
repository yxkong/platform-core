package com.github.platform.core.workflow.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * form表单信息
 * @author: yxkong
 * @date: 2024/3/12 17:37
 * @version: 1.0
 */
@Schema(description = "form表单信息")
@Data
@Builder
public class FormViewAssemblyDto implements Serializable {
    private String title;
    private Boolean readOnly;
    private List<FormInfoDto> list;

    public FormViewAssemblyDto(String title, List<FormInfoDto> list) {
        this.title = title;
        this.list = list;
    }

    public FormViewAssemblyDto(String title, Boolean readOnly, List<FormInfoDto> list) {
        this.title = title;
        this.readOnly = readOnly;
        this.list = list;
    }
}
