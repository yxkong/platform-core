package com.github.platform.core.workflow.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 流程实例状态
 * @author: yxkong
 * @date: 2023/11/2 11:23
 * @version: 1.0
 */
@AllArgsConstructor
@Getter
public enum InstanceStatusEnum {
    ERROR(-1,"异常"),
    INIT(0,"初始化"),
    ACTIVE(1,"运行中"),
    SUSPEND(2,"挂起"),
    COMPLETED(3,"完成"),
    CANCELLED(4,"取消/停止/废弃"),
    ;

    private Integer status;
    private String desc;

    /**
     * 运行中
     * @param status
     * @return
     */
    public static boolean isRun(Integer status){
        return InstanceStatusEnum.ACTIVE.getStatus().equals(status) || InstanceStatusEnum.INIT.getStatus().equals(status);
    }
    public static boolean isHangUp(Integer status){
        return InstanceStatusEnum.SUSPEND.getStatus().equals(status);
    }
    public boolean isHangUp(){
        return isHangUp(status);
    }
    public static boolean isComplete(Integer status){
        return InstanceStatusEnum.COMPLETED.getStatus().equals(status);
    }
    public boolean isComplete(){
        return isComplete(this.status);
    }
    public static boolean isCancelled(Integer status){
        return InstanceStatusEnum.CANCELLED.getStatus().equals(status);
    }
    public boolean isCancelled(){
        return isCancelled(this.status);
    }
    public static boolean isFinished(Integer status){
        return isComplete(status) || isCancelled(status);
    }
}
