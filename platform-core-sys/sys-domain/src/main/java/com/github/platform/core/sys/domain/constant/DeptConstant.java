package com.github.platform.core.sys.domain.constant;

import com.github.platform.core.standard.constant.StatusEnum;

/**
 * 部门相关常量
 * @author: yxkong
 * @date: 2023/4/7 11:32 AM
 * @version: 1.0
 */
public interface DeptConstant {
    /**
     * 部门根节点
     */
    Long ROOT_ID = 0L;
    /**默认部门id
     * */
    Long DEFAULT_ID = -1L;
    /** 部门正常状态 */
    Integer DEPT_NORMAL = StatusEnum.ON.getStatus();

    /** 部门停用状态 */
    Integer DEPT_DISABLE = StatusEnum.OFF.getStatus();
}
