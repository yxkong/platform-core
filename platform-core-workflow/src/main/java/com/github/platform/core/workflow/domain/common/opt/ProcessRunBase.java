package com.github.platform.core.workflow.domain.common.opt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.common.utils.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 流程创建基础
 *
 * @author: yxkong
 * @date: 2023/11/1 10:27
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessRunBase implements Serializable {
    /**租户编号*/
    protected Integer tenantId;
    /** 业务编号 */
    protected String bizNo;
    /**实例名称,业务名称*/
    protected String  instanceName;
    /**实例编号*/
    protected String  instanceNo;
    /** 流程编码 */
    @NotEmpty(message="流程编码（processNo）不能为空")
    protected String processNo;
    /**
     * 流程发起人，为登录账户
     */
    protected String initiator;
    protected Long deptId;

    /**
     * 流程变量,必须有FlwConstant.APPROVAL_NO
     */
    protected Map<String, Object> variables = new HashMap<>();

    /**
     * 设置变量
     * @param key
     * @param obj
     */
    @JsonIgnore
    public void putParam(String key,Object obj){
        if (CollectionUtil.isEmpty(this.variables)){
            this.variables = new HashMap<>();
        }
        this.variables.put(key,obj);
    }

}
