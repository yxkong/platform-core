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
@AllArgsConstructor
@Builder
public class FormDataViewDto implements Serializable {
    private String title;
    private Boolean isCheck;
    private List<FormInfoDto> list;
}
