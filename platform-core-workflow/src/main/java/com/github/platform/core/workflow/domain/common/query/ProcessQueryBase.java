package com.github.platform.core.workflow.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 流程查询
 * @author: yxkong
 * @date: 2023/11/13 14:18
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "流程查询")
public class ProcessQueryBase extends PageQueryBaseEntity {
    /**
     * 审批人
     */
    private String assignee;
    /**
     * 角色key，项目流程是key，其他流程可以用roleId
     */
    private List<String> roles;
    /**
     * 实例标识
     */
    private String instanceNo;
    /**
     * 实例名称
     */
    private String instanceName;
    /**
     * 流程定义编号
     */
    private String processNo;
    /**
     * 流程类型
     */
    private String processType;
    /**
     * 业务编号
     */
    private String bizNo;

    /**
     * 实例名称
     */
    private String taskName;

    /**
     * 流程分类
     */
    private String category;

    /**
     * 状态
     */
    private String state;

}
