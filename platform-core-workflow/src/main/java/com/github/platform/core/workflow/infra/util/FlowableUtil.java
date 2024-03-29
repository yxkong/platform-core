package com.github.platform.core.workflow.infra.util;

import com.github.platform.core.standard.exception.InfrastructureException;
import org.flowable.bpmn.model.*;
import org.flowable.common.engine.impl.identity.Authentication;

import java.util.*;

/**
 * 流程引擎工具类
 * @author: yxkong
 * @date: 2023/9/21 10:30 AM
 * @version: 1.0
 */
public class FlowableUtil {

    public static void setAuthenticatedLoginName(String loginName) {
        Authentication.setAuthenticatedUserId(loginName);
    }

    public static void clearAuthenticatedLoginName() {
        Authentication.setAuthenticatedUserId(null);
    }
    /**
     * 深搜递归获取流程未通过的节点
     * @param bpmnModel 流程模型
     * @param unfinishedTaskSet 未结束的任务节点
     * @param finishedSequenceFlowSet 已经完成的连线
     * @param finishedTaskSet 已完成的任务节点
     * @return
     */
    public static Set<String> dfsFindRejects(BpmnModel bpmnModel, Set<String> unfinishedTaskSet, Set<String> finishedSequenceFlowSet, Set<String> finishedTaskSet) {
        if (Objects.isNull(bpmnModel)) {
            throw new InfrastructureException(null);
        }
        Collection<FlowElement> allElements = BpmnModelUtils.getAllElements(bpmnModel);
        Set<String> rejectedSet = new HashSet<>();
        allElements.forEach(s->{
            if (s instanceof UserTask && unfinishedTaskSet.contains(s.getId())) {
                Set<String> hasSequenceFlow = iteratorFindFinishes(s, null);
                Set<String> rejects = iteratorFindRejects(s, finishedSequenceFlowSet, finishedTaskSet, hasSequenceFlow, null);
                rejectedSet.addAll(rejects);
            }
        });
        return rejectedSet;
    }
    /**
     * 迭代获取父级节点列表，向前找
     * @param source 起始节点
     * @param visitedSequenceFlows 已经经过的连线的ID，用于判断线路是否重复
     * @return
     */
    private static Set<String> iteratorFindFinishes(FlowElement source, Set<String> visitedSequenceFlows) {
        // 初始化 Set，避免空指针异常
        visitedSequenceFlows = visitedSequenceFlows == null ? new HashSet<>() : visitedSequenceFlows;

        // 根据类型，获取入口连线
        List<SequenceFlow> sequenceFlows = BpmnModelUtils.getElementIncomingFlows(source);

        if (sequenceFlows != null) {
            // 循环找到目标元素
            for (SequenceFlow sequenceFlow : sequenceFlows) {
                // 如果发现连线重复，说明循环了，跳过这个循环
                if (visitedSequenceFlows.contains(sequenceFlow.getId())) {
                    continue;
                }
                // 添加已经走过的连线
                visitedSequenceFlows.add(sequenceFlow.getId());
                FlowElement finishedElement = sequenceFlow.getSourceFlowElement();
                // 类型为子流程，则添加子流程开始节点出口处相连的节点
                if (finishedElement instanceof SubProcess) {
                    FlowElement firstElement = ((SubProcess) finishedElement).getFlowElements().iterator().next();
                    // 获取子流程的连线
                    visitedSequenceFlows.addAll(iteratorFindFinishes(firstElement, new HashSet<>()));
                }
                // 继续迭代
                visitedSequenceFlows = iteratorFindFinishes(finishedElement, visitedSequenceFlows);
            }
        }
        return visitedSequenceFlows;
    }

    /**
     * 根据正在运行的任务节点，迭代获取子级任务节点列表，向后找
     * @param source 起始节点
     * @param finishedSequenceFlowSet 已经完成的连线
     * @param finishedTaskSet 已经完成的任务节点
     * @param hasSequenceFlow 已经经过的连线的 ID，用于判断线路是否重复
     * @param rejectedList 未通过的元素
     * @return
     */
    private static Set<String> iteratorFindRejects(FlowElement source, Set<String> finishedSequenceFlowSet, Set<String> finishedTaskSet,
                                                   Set<String> hasSequenceFlow, Set<String> rejectedList) {
        // 初始化 Set，避免空指针异常
        hasSequenceFlow = hasSequenceFlow == null ? new HashSet<>() : hasSequenceFlow;
        rejectedList = rejectedList == null ? new HashSet<>() : rejectedList;

        // 根据类型，获取出口连线
        List<SequenceFlow> sequenceFlows = BpmnModelUtils.getElementOutgoingFlows(source);

        if (sequenceFlows != null) {
            // 循环找到目标元素
            for (SequenceFlow sequenceFlow : sequenceFlows) {
                // 如果发现连线重复，说明循环了，跳过这个循环
                if (hasSequenceFlow.contains(sequenceFlow.getId())) {
                    continue;
                }
                // 添加已经走过的连线
                hasSequenceFlow.add(sequenceFlow.getId());
                FlowElement targetElement = sequenceFlow.getTargetFlowElement();
                // 添加未完成的节点
                if (finishedTaskSet.contains(targetElement.getId())) {
                    rejectedList.add(targetElement.getId());
                }
                // 添加未完成的连线
                if (finishedSequenceFlowSet.contains(sequenceFlow.getId())) {
                    rejectedList.add(sequenceFlow.getId());
                }
                // 如果节点为子流程节点情况，则从节点中的第一个节点开始获取
                if (targetElement instanceof SubProcess) {
                    FlowElement firstElement = ((SubProcess) targetElement).getFlowElements().iterator().next();
                    // 如果找到节点，则说明该线路找到节点，不继续向下找，反之继续
                    if (!iteratorFindRejects(firstElement, finishedSequenceFlowSet, finishedTaskSet, hasSequenceFlow, new HashSet<>()).isEmpty()) {
                        rejectedList.addAll(iteratorFindRejects(firstElement, finishedSequenceFlowSet, finishedTaskSet, hasSequenceFlow, new HashSet<>()));
                        continue;
                    }
                }
                // 继续迭代
                rejectedList = iteratorFindRejects(targetElement, finishedSequenceFlowSet, finishedTaskSet, hasSequenceFlow, rejectedList);
            }
        }
        return rejectedList;
    }

}
