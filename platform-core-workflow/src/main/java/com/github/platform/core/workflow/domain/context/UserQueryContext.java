package com.github.platform.core.workflow.domain.context;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 用户查询上下文
 * @Author: yxkong
 * @Date: 2023/12/11 19:20
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class UserQueryContext {
    private List<String> roles;
    /**节点id*/
    private String nodeKey;
    /**节点名称*/
    private String nodeName;
    /**业务编号*/
    private String bizNo;
    /**流程类型*/
    private String processType;
    /**实例id*/
    private  String instanceId;
}
