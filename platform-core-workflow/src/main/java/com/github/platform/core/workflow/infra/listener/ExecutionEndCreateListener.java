package com.github.platform.core.workflow.infra.listener;

import com.github.platform.core.common.utils.ApplicationContextHolder;
import com.github.platform.core.workflow.domain.constant.FlwConstant;
import com.github.platform.core.workflow.domain.constant.InstanceStatusEnum;
import com.github.platform.core.workflow.domain.constant.ProcessTypeEnum;
import com.github.platform.core.workflow.domain.gateway.IProcessInstanceGateway;
import com.github.platform.core.workflow.infra.event.WorkflowProcessEvent;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;

/**
 * 结束节点创建时间，需流程节点中配置，目前是程序注入
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
 * @author: yxkong
 * @date: 2023/11/8 13:45
 * @version: 1.0
 */
@Slf4j
public class ExecutionEndCreateListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegate) {
        String eventName = delegate.getEventName();
        String instanceId = delegate.getProcessInstanceId();
        String instanceNo = (String) delegate.getVariable(FlwConstant.INSTANCE_NO);
        String bizNo = (String) delegate.getVariable(FlwConstant.BIZ_NO);
        String processType = (String) delegate.getVariable(FlwConstant.PROCESS_TYPE);
        if (!ProcessTypeEnum.isPm(processType)){
            return;
        }
        if (log.isDebugEnabled()){
            log.debug("end节点触发：instanceId:{} instanceNo:{} bizNo:{}  eventName:{}",instanceId,instanceNo,bizNo,eventName);
        }
        IProcessInstanceGateway processInstanceGateway = ApplicationContextHolder.getBean(IProcessInstanceGateway.class);
        processInstanceGateway.updateByInstanceId(instanceId, InstanceStatusEnum.COMPLETED);
        ApplicationContext applicationContext = ApplicationContextHolder.getApplicationContext();
        applicationContext.publishEvent(new WorkflowProcessEvent(instanceId,bizNo,processType,InstanceStatusEnum.COMPLETED));
    }
}
