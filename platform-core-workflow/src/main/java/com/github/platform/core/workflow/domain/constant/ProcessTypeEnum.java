package com.github.platform.core.workflow.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 流程类型枚举
 * @author: yxkong
 * @date: 2023-02-22 17:18
 * @Description: 审批流状态枚举
 */
@AllArgsConstructor
@Getter
public enum ProcessTypeEnum {
    PM("pm","pmRoleStrategy", "pmUserStrategy","pmFormDataGateway","项目"),
    OA("oa","sysRoleStrategy","sysUserStrategy","sysFormDataGateway","OA流程")
    ;

    private String type;
    private String roleBean;
    private String userBean;
    private String formBean;
    private String desc;

    public static ProcessTypeEnum get(String type){
        for (ProcessTypeEnum x:ProcessTypeEnum.values()){
            if (x.type.equals(type)){
                return x;
            }
        }
        return ProcessTypeEnum.OA;
    }
    public static boolean isPm(String type){
        return PM.type.equals(type);
    }
}
