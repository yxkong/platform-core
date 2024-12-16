package com.github.platform.core.message.dingtalk.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *钉钉部门信息
 * @author
 */
@Data
public class DingDeptDto {

    /**部门ID*/
    @JsonProperty("dept_id")
    private Long deptId;
    /**部门名称*/
    private String name;
    /**父部门ID*/
    @JsonProperty("parent_id")
    private Long parentId;
    private String ext;
}
