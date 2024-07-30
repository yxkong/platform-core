package com.github.platform.core.workflow.infra.util;

import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import feign.form.util.CharsetUtil;
import org.assertj.core.util.Lists;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;
import org.flowable.common.engine.impl.util.io.StringStreamSource;

import java.util.*;

/**
 * @author: yxkong
 * @date: 2023-02-23 15:08
 * @Description: bpmnModel工具
 */
public class BpmnModelUtils {
    private static final BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    /**
     * xml转bpmnModel对象
     *
     * @param xml xml
     * @return bpmnModel对象
     */
    public static BpmnModel getBpmnModel(String xml) {
        return bpmnXMLConverter.convertToBpmnModel(new StringStreamSource(xml), false, false);
    }
    /**
     * bpmnModel转xml字符串
     *
     * @param bpmnModel bpmnModel对象
     * @return xml字符串
     */
    public static String getBpmnXmlStr(BpmnModel bpmnModel) {
        return new String(getBpmnXml(bpmnModel),  CharsetUtil.UTF_8);
    }

    /**
     * bpmnModel转xml对象
     *
     * @param bpmnModel bpmnModel对象
     * @return xml
     */
    public static byte[] getBpmnXml(BpmnModel bpmnModel) {
        return bpmnXMLConverter.convertToXML(bpmnModel);
    }

    /**
     * 根据节点，获取入口连线
     *
     * @param source 起始节点
     * @return 入口连线列表
     */
    public static List<SequenceFlow> getElementIncomingFlows(FlowElement source) {
        List<SequenceFlow> sequenceFlows = new ArrayList<>();
        if (source instanceof FlowNode) {
            sequenceFlows = ((FlowNode) source).getIncomingFlows();
        }
        return sequenceFlows;
    }


    /**
     * 根据节点，获取出口连线
     *
     * @param source 起始节点
     * @return 出口连线列表
     */
    public static List<SequenceFlow> getElementOutgoingFlows(FlowElement source) {
        List<SequenceFlow> sequenceFlows = new ArrayList<>();
        if (source instanceof FlowNode) {
            sequenceFlows = ((FlowNode) source).getOutgoingFlows();
        }
        return sequenceFlows;
    }

    /**
     * 获取所有条件网关出口连线
     *
     * @param model bpmnModel对象
     * @return
     */
    public static Collection<SequenceFlow> getAllExclusiveGatewayOutgoings(BpmnModel model) {
        List<ExclusiveGateway> gateways = getAllExclusiveGateways(model);
        List<SequenceFlow> sequenceFlows = Lists.newArrayList();
        gateways.forEach(flowElement -> {
            sequenceFlows.addAll(getElementOutgoingFlows(flowElement));
        });
        return sequenceFlows;
    }
    public static Collection<FlowElement> getAllElements(BpmnModel model) {

        Collection<FlowElement> all = model.getMainProcess().getFlowElements();
        all.addAll(getSubElements(all));
        return all;
    }
    private static Collection<FlowElement> getSubElements(Collection<FlowElement> flowElements) {
        Collection<FlowElement> rst = new ArrayList<>();
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof SubProcess) {
                // 递归获取子节点
                rst.addAll(getSubElements(((SubProcess) flowElement).getFlowElements())) ;
            }
        }
        return rst;
    }

    /**
     * 获取所有条件网关
     *
     * @param model bpmnModel对象
     * @return
     */
    public static List<ExclusiveGateway> getAllExclusiveGateways(BpmnModel model) {
        Process process = model.getMainProcess();
        Collection<FlowElement> flowElements = process.getFlowElements();
        List<ExclusiveGateway> gateways = Lists.newArrayList();
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof ExclusiveGateway) {
                gateways.add((ExclusiveGateway) flowElement);
            }
        }
        return gateways;
    }

    /**
     * 获取所有开始节点
     * @param bpmnModel
     * @return
     */
    public static Collection<StartEvent> getAllStartEvent(BpmnModel bpmnModel) {
        Collection<StartEvent> list = new ArrayList<StartEvent>();
        Process process = bpmnModel.getMainProcess();
        FlowElement startElement = process.getInitialFlowElement();
        if (startElement instanceof StartEvent) {
            list.add((StartEvent) startElement);
        }
        getStartEvent(process.getFlowElements(),list);
        return list;
    }
    /**
     * 获取所有开始节点
     *
     * @param flowElements 流程元素集合
     * @return
     */
    public static void getStartEvent(Collection<FlowElement> flowElements, Collection<StartEvent> list) {
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof StartEvent) {
                list.add((StartEvent) flowElement);
            }
        }
    }
    /**
     * 获取开始节点
     *
     * @param flowElements 流程元素集合
     * @return 开始节点（未找到开始节点，返回null）
     */
    public static StartEvent getStartEvent(Collection<FlowElement> flowElements) {
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof StartEvent) {
                return (StartEvent) flowElement;
            }
        }
        return null;
    }


    /**
     * 获取开始节点
     *
     * @param model bpmnModel对象
     * @return 开始节点（未找到开始节点，返回null）
     */
    public static StartEvent getStartEvent(BpmnModel model) {
        Process process = model.getMainProcess();
        FlowElement startElement = process.getInitialFlowElement();
        if (startElement instanceof StartEvent) {
            return (StartEvent) startElement;
        }
        return getStartEvent(process.getFlowElements());
    }

    /**
     * 获取第一个结束节点
     *
     * @param model bpmnModel对象
     * @return 结束节点（未找到开始节点，返回null）
     */
    public static EndEvent getEndEvent(BpmnModel model) {
        Process process = model.getMainProcess();
        return getEndEvent(process.getFlowElements());
    }

    /**
     * 获取第一个结束节点
     *
     * @param flowElements 流程元素集合
     * @return 结束节点（未找到开始节点，返回null）
     */
    public static EndEvent getEndEvent(Collection<FlowElement> flowElements) {
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof EndEvent) {
                return (EndEvent) flowElement;
            }
        }
        return null;
    }

    /**
     * 获取全部结束节点
     *
     * @param model bpmnModel对象
     * @return 所有结束节点（未找到，返回null）
     */
    public static Collection<EndEvent> getAllEndEvent(BpmnModel model) {
        Process process = model.getMainProcess();
        return getAllEndEvent(process.getFlowElements());
    }

    /**
     * 获取全部结束节点
     *
     * @param flowElements 流程元素集合
     * @return 所有结束节点（未找到，返回null）
     */
    public static Collection<EndEvent> getAllEndEvent(Collection<FlowElement> flowElements) {
        Collection<EndEvent> list = new ArrayList<EndEvent>();
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof EndEvent) {
                list.add((EndEvent) flowElement);
            }
        }
        return CollectionUtil.isEmpty(list) ? null : list;
    }


    public static UserTask getUserTaskByKey(BpmnModel model, String taskKey) {
        Process process = model.getMainProcess();
        FlowElement flowElement = process.getFlowElement(taskKey);
        if (flowElement instanceof UserTask) {
            return (UserTask) flowElement;
        }
        return null;
    }

    /**
     * 获取流程元素信息
     *
     * @param model         bpmnModel对象
     * @param flowElementId 元素ID，也是指定的节点
     * @return 元素信息
     */
    public static FlowElement getFlowElementById(BpmnModel model, String flowElementId) {
        Process process = model.getMainProcess();
        return process.getFlowElement(flowElementId);
    }

    /**
     * 获取起始节点的form表单key
     * @param model
     * @return
     */
    public static String getStartEventFormKey(BpmnModel model){
        StartEvent startEvent = getStartEvent(model);
        return getFormKey(startEvent);
    }

    /**
     * 获取表单编号，bpmn会加前缀
     * @param formKey
     * @return
     */
    public static String getFormNo(String formKey){
        if (StringUtils.isNotEmpty(formKey)){
            return formKey.replace("key_","");
        }
        return null;
    }
    /**
     * 获取 用户节点的form表单key
     * @param model
     * @return
     */
    public static List<FormKey> getAllUserTaskFormKey(BpmnModel model){
        List<FormKey> rst = new ArrayList<>();
        Collection<UserTask> allUserTaskEvent = getAllUserTaskEvent(model);
        if (CollectionUtil.isNotEmpty(allUserTaskEvent)){
            allUserTaskEvent.forEach(s->{
                String formKey = getFormKey(s);
                if (StringUtils.isNotEmpty(formKey)){
                    rst.add(new FormKey(formKey,s.getId(),s.getName()));
                }
            });
        }
        return rst;
    }
    public static class FormKey {
        //节点的form表单
        private String formKey;
        //节点id
        private String id;
        //节点的中文名称
        private String name;

        public FormKey(String formKey, String id, String name) {
            this.formKey = formKey;
            this.id = id;
            this.name = name;
        }

        public String getFormKey() {
            return formKey;
        }

        public String getName() {
            return name;
        }
        public String getNodeKey() {
            return id;
        }
    }

    /**
     * 获取元素表单Key（限开始节点和用户节点可用）
     *
     * @param flowElement 元素
     * @return 表单Key
     */
    public static String getFormKey(FlowElement flowElement) {
        if (flowElement != null) {
            if (flowElement instanceof StartEvent) {
                return ((StartEvent) flowElement).getFormKey();
            } else if (flowElement instanceof UserTask) {
                return ((UserTask) flowElement).getFormKey();
            }
        }
        return null;
    }

    /**
     * 获取开始节点属性值
     *
     * @param model bpmnModel对象
     * @param name  属性名
     * @return 属性值
     */
    public static String getStartEventAttributeValue(BpmnModel model, String name) {
        StartEvent startEvent = getStartEvent(model);
        return getElementAttributeValue(startEvent, name);
    }

    /**
     * 获取结束节点属性值
     *
     * @param model bpmnModel对象
     * @param name  属性名
     * @return 属性值
     */
    public static String getEndEventAttributeValue(BpmnModel model, String name) {
        EndEvent endEvent = getEndEvent(model);
        return getElementAttributeValue(endEvent, name);
    }


    /**
     * 获取用户任务节点属性值
     *
     * @param model   bpmnModel对象
     * @param taskKey 任务Key
     * @param name    属性名
     * @return 属性值
     */
    public static String getUserTaskAttributeValue(BpmnModel model, String taskKey, String name) {
        UserTask userTask = getUserTaskByKey(model, taskKey);
        return getElementAttributeValue(userTask, name);
    }

    /**
     * 获取元素属性值
     *
     * @param baseElement 流程元素
     * @param name        属性名
     * @return 属性值
     */
    public static String getElementAttributeValue(BaseElement baseElement, String name) {
        if (baseElement != null) {
            List<ExtensionAttribute> attributes = baseElement.getAttributes().get(name);
            if (attributes != null && !attributes.isEmpty()) {
                attributes.iterator().next().getValue();
                Iterator<ExtensionAttribute> attrIterator = attributes.iterator();
                if (attrIterator.hasNext()) {
                    ExtensionAttribute attribute = attrIterator.next();
                    return attribute.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 获取BaseElement的扩展属性，后续还有其他类型的
     * @param model
     * @param flowElementId
     * @return
     */
    public static Map<String,String> getElementExtendAttributeById(BpmnModel model, String flowElementId){
        return getElementExtendAttribute(getFlowElementById(model,flowElementId));
    }

    /**
     * 获取节点属性和扩展属性
     * @param model
     * @param flowElementId
     * @return
     */
    public static Map<String,String> getElementAllAttribute(BpmnModel model, String flowElementId){
        FlowElement flowElement = getFlowElementById(model, flowElementId);
        Map<String, String> map = getElementAttribute(flowElement);
        Map<String, String> attribute = getElementExtendAttribute(flowElement);
        if (CollectionUtil.isNotEmpty(attribute)){
            map.putAll(attribute);
        }
        return map;
    }

    /**
     * 获取BaseElement 元素中的扩展属性
     * @param flowElement
     * @return
     */
    public static  Map<String,String> getElementExtendAttribute(FlowElement flowElement){
        Map<String,String> rst = new HashMap<>();
        if (Objects.isNull(flowElement)){
            return rst;
        }
        List<ExtensionElement> list1 = flowElement.getExtensionElements().get("properties");
        if (CollectionUtil.isEmpty(list1)){
            return rst;
        }
        // 获取BaseElement 中的 extensionElements
        List<ExtensionElement> list = list1.get(0).getChildElements().get("property");
        if (CollectionUtil.isNotEmpty(list)){
            list.forEach(e->{
                String name = e.getAttributeValue(null, "name");
                if (StringUtils.isNotEmpty(name)){
                    String value = e.getAttributeValue(null, "value");
                    // 转小写
                    rst.put(name.toLowerCase(),value);
                }
            });
        }
        return rst;
    }
    /**
     * 获取BpmnModel 中指定节点flowElementId 的属性
     * @param model
     * @param flowElementId
     * @return
     */
    public static Map<String,String> getElementAttributeById(BpmnModel model, String flowElementId){
        return getElementAttribute(getFlowElementById(model,flowElementId));
    }


    public static Map<String,String> getElementAttribute(BaseElement baseElement){
        Map<String,String> rst = new HashMap<>();
        if (Objects.isNull(baseElement)){
            return rst;
        }

        Map<String, List<ExtensionAttribute>> attributes = baseElement.getAttributes();
        if (CollectionUtil.isEmpty(attributes)){
            return rst;
        }
        attributes.forEach((k,v)->{
            if (CollectionUtil.isNotEmpty(v)){
                // 取第一个 ExtensionAttribute 的值，如果有多个可以根据需求进行处理
                ExtensionAttribute extensionAttribute = v.get(0);
                if (Objects.nonNull(extensionAttribute)) {
                    rst.put(k.toLowerCase(), extensionAttribute.getValue());
                }
            }
        });
        return rst;
    }

    public static boolean isMultiInstance(BpmnModel model, String taskKey) {
        UserTask userTask = getUserTaskByKey(model, taskKey);
        if (Objects.nonNull(userTask)) {
            return userTask.hasMultiInstanceLoopCharacteristics();
        }
        return false;
    }

    /**
     * 获取所有用户任务节点
     *
     * @param model bpmnModel对象
     * @return 用户任务节点列表
     */
    public static Collection<UserTask> getAllUserTaskEvent(BpmnModel model) {
        Process process = model.getMainProcess();
        Collection<FlowElement> flowElements = process.getFlowElements();
        return getAllUserTaskEvent(flowElements, null);
    }

    /**
     * 获取所有用户任务节点
     *
     * @param flowElements 流程元素集合
     * @param allElements  所有流程元素集合
     * @return 用户任务节点列表
     */
    public static Collection<UserTask> getAllUserTaskEvent(Collection<FlowElement> flowElements, Collection<UserTask> allElements) {
        allElements = allElements == null ? new ArrayList<>() : allElements;
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof UserTask) {
                allElements.add((UserTask) flowElement);
            }
            if (flowElement instanceof SubProcess) {
                // 继续深入子流程，进一步获取子流程
                allElements = getAllUserTaskEvent(((SubProcess) flowElement).getFlowElements(), allElements);
            }
        }
        return allElements;
    }

    /**
     * 查找起始节点下一个用户任务列表列表
     *
     * @param source 起始节点
     * @return 结果
     */
    public static List<UserTask> findNextUserTasks(FlowElement source) {
        return findNextUserTasks(source, null, null);
    }


    /**
     * 查找起始节点下一个用户任务列表列表
     *
     * @param source          起始节点
     * @param hasSequenceFlow 已经经过的连线的 ID，用于判断线路是否重复
     * @param userTaskList    用户任务列表
     * @return 结果
     */
    public static List<UserTask> findNextUserTasks(FlowElement source, Set<String> hasSequenceFlow, List<UserTask> userTaskList) {
        hasSequenceFlow = Optional.ofNullable(hasSequenceFlow).orElse(new HashSet<>());
        userTaskList = Optional.ofNullable(userTaskList).orElse(new ArrayList<>());
        // 获取出口连线
        List<SequenceFlow> sequenceFlows = getElementOutgoingFlows(source);
        if (!sequenceFlows.isEmpty()) {
            for (SequenceFlow sequenceFlow : sequenceFlows) {
                // 如果发现连线重复，说明循环了，跳过这个循环
                if (hasSequenceFlow.contains(sequenceFlow.getId())) {
                    continue;
                }
                // 添加已经走过的连线
                hasSequenceFlow.add(sequenceFlow.getId());
                FlowElement targetFlowElement = sequenceFlow.getTargetFlowElement();
                if (targetFlowElement instanceof UserTask) {
                    // 若节点为用户任务，加入到结果列表中
                    userTaskList.add((UserTask) targetFlowElement);
                } else {
                    // 若节点非用户任务，继续递归查找下一个节点
                    findNextUserTasks(targetFlowElement, hasSequenceFlow, userTaskList);
                }
            }
        }
        return userTaskList;
    }
    /**
     * 用户节点多人或签时字符长度
     *
     * @param userTask
     * @return
     */
    public static int getCandidateUsersLength(UserTask userTask) {
        //表示单人
        if (userTask.getCandidateUsers().size() == 0) {
            return 0;
        }
        return userTask.getCandidateUsers().stream().mapToInt(String::length).sum();
    }
}
