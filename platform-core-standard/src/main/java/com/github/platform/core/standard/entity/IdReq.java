package com.github.platform.core.standard.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 通用id请求
 * @author: yxkong
 * @date: 2023/3/22 10:08 AM
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class  IdReq implements Serializable {
    @NotNull(message = "主键id不能为空")
    @Schema(description = "主键id")
    private Long id;
}
