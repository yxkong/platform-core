package com.github.platform.core.workflow.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 流程定义状态
 * @author: yxkong
 * @date: 2023-02-22 17:18
 * @Description: 审批流状态枚举
 */
@AllArgsConstructor
@Getter
public enum ProcessStatusEnum {
    DRAFT(0, "草稿"),
    ON(1,"激活"),
    OFF(2,"挂起/停用")
    ;

    private Integer status;
    private String desc;

    public boolean isOn(){
        return ProcessStatusEnum.ON.getStatus().equals(this.status);
    }
    public boolean isOff(){
        return ProcessStatusEnum.OFF.getStatus().equals(this.status);
    }
}
