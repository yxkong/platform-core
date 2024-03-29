package com.github.platform.core.workflow.domain.constant;

import com.github.platform.core.workflow.infra.listener.ExecutionEndCreateListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.flowable.engine.delegate.ExecutionListener;

/**
 * 审批流监听器枚举及配置
 *
 * 单用户任务处理监听
 * TaskListener 监听用户任务的生命周期，需要用户或系统需要执行的时候，适用于 UserTask<br>
 *  生命周期节点见  {@link org.flowable.task.service.delegate.BaseTaskListener} <br>
 *  执行顺序：assignment 分配办理人 → create 创建 → complete 完成 → delete 删除
 *  - `create`（创建任务时） 只执行一次
 *  - `assignment`（任务分配时） 任务每次分配给用户/组时执行一次
 *  - `complete`（任务完成时） 只执行一次
 *  - `delete` (任务删除时)
 *  - `all` (所有任务)
 *
 * ExecutionListener  用于监听流程实例的生命周期事件
 * 事件生命周期： 见{@link org.flowable.engine.delegate.BaseExecutionListener}
 *
 * @author yxkong
 */
@AllArgsConstructor
@Getter
public enum ProcessListenerEnum {

    /**用户任务创建**/
//    USER_TASK_CREATE(TaskListener.EVENTNAME_CREATE,"userTask", UserTaskCreateListener.class.getName(),"用户任务创建"),
    /**用户任务完成*/
//    USER_TASK_COMPLETE(TaskListener.EVENTNAME_COMPLETE,"userTask", UserTaskCompleteListener.class.getName(),"用户任务完成"),
    /**start的节点优先于  create */
//    USER_TASK_START(ExecutionListener.EVENTNAME_START,"userTask", ExecutionStartListener.class.getName(),"用户任务Start"),
    /**结束任务创建即结束**/
    END_TASK_START(ExecutionListener.EVENTNAME_START,"endTask", ExecutionEndCreateListener.class.getName(),"结束节点创建")
    ;
    /**监听事件**/
    private String event;
    /**监听事件类型**/
    private String type;
    /**
     * 监听事件实现
     */
    private String path;
    /**
     * 监听器描述
     */
    private String desc;
}