package com.github.platform.core.standard.entity.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


/**
 * 分页查询通用上下文
 * @author: yxkong
 * @date: 2023/3/23 3:23 PM
 * @version: 1.0
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public class PageQueryContextBase {
    /**
     * 状态(1启用 0停用)
     */
    protected Integer status ;
    /**
     * 租户
     */
    private Integer tenantId;
    /**
     * 开始时间
     */
    protected String searchStartTime;
    /**
     * 结束时间
     */
    protected String searchEndTime;

    /**
     * 页码
     */
    private Integer pageNum;
    /**
     * 每页条数
     */
    private Integer pageSize;
}
