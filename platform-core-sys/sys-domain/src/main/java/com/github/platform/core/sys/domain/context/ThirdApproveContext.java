package com.github.platform.core.sys.domain.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 三方账户审批上下文
 * @author: yxkong
 * @date: 2023/6/2 11:26 AM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdApproveContext {
    private Long id;
    /** 绑定用户id */
    private Long userId;
    private Long deptId;
    private String[] roleKeys;
    private Integer status;
}
