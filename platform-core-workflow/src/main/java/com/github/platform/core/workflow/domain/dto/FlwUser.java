package com.github.platform.core.workflow.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 流程用户
 * @author: yxkong
 * @date: 2023/10/30 13:53
 * @version: 1.0
 */
@Schema(description = "流程用户")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlwUser {
    /**
     * 登陆名称
     */
    @Schema(description ="登陆名称")
    private String loginName;

    /**
     * 用户姓名
     */
    @Schema(description ="用户姓名")
    private String userName;
    /**
     * 部门名称
     */
    @Schema(description ="部门名称")
    private String deptName;
}
