package com.github.platform.core.message.dingtalk.feign.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 钉钉部门查询
 * @author: yxkong
 * @date: 2024/1/19 13:52
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DingDeptQuery {
    /**父部门ID*/
    @JsonProperty("dept_id")
    private Long deptId;
    /**通讯录语言*/
    @Builder.Default
    private String language = "zh_CN";
}
