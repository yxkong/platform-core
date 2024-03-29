package com.github.platform.core.dingtalk.infra.rpc.external.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取部门用户
 * @author: yxkong
 * @date: 2024/1/19 14:40
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DingDeptUserQuery {
    @JsonProperty("dept_id")
    private Long deptId;
    /**分页查询的游标，最开始传0，后续传返回参数中的next_cursor值*/
    @Builder.Default
    private Integer cursor = 0;
    /**分页大小*/
    @Builder.Default
    private Integer size = 50;
    /**部门成员的排序规则
     *  entry_asc：代表按照进入部门的时间升序
     *  entry_desc：代表按照进入部门的时间降序
     *  modify_asc：代表按照部门信息修改时间升序
     *  modify_desc：代表按照部门信息修改时间降序
     *  custom：代表用户定义(未定义时按照拼音)排序
     * */
    @JsonProperty("order_field")
    private String orderField = "modify_desc";
    @JsonProperty("contain_access_limit")
    @Builder.Default
    private Boolean containAccessLimit = false;

    /**通讯录语言*/
    @Builder.Default
    private String language = "zh_CN";
}
