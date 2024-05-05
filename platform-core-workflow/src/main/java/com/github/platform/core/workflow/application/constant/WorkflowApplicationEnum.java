package com.github.platform.core.workflow.application.constant;

import com.github.platform.core.standard.exception.BaseResult;

/**
 * 流程应用层异常枚举
 * @author: yxkong
 * @date: 16023/9/25 10:12 AM
 * @version: 1.0
 */
public enum WorkflowApplicationEnum implements BaseResult {
    PROCESS_INSTANCE_NO_FOUND("1000","未查询到对应的流程实例！" ),
    definition_no_found("1000","未查询到对应的流程定义！" ),
    cannot_modify("16001","非草稿状态审批流不允许修改" ),
    bpmn_is_null("16002","流程文件为空" ),
    bpmn_parser_error("16003","流程文件解析异常！" ),

    bpmn_size_error("16004","审批流文件不合法，请检查审批流" ),
    bpmn_user_task_error("16005","审批流用户节点缺失，请检查审批流" ),
    bpmn_assignee_error1("16006","审批流节点:%s ，未配置正确的审批用户" ),
    bpmn_assignee_error2("16006","审批流节点:%s ，未配置正确的审批角色" ),
    bpmn_assignee_error3("16006","审批流暂未实现部门审批" ),
    bpmn_assignee_error4("16006","审批流节点:%s ，未配置正确的审批人" ),
    incoming_flows("16007", "审批流用户节点:%s连接线异常，请检查审批流流入线"),
    outgoing_flows("16008", "审批流用户节点:%s ，连接线异常，请检查审批流流出线"),
    require_exclusive_gateway("16009","审批流条件网关配置异常（小于2个节点），请检查是否需要网关！" ),
    sequence_not_null("16010", "审批流网关条件配置缺失，请检查审批流网关节点条件！"),
    require_start("16011","审批流缺少开始节点，请检查审批流" ),
    require_start_only_one("16012","审批流仅支持一个开始节点，请检查审批流" ),
    require_start_only_out_one("16013", "审批流开始节点连接线异常，请检查审批流"),
    require_end_only_one("16014","审批流缺少结束节点，请检查审批流" ),
    require_end_only_in_one("16015", "审批流結束节点连接线异常，请检查审批流"),
    update_process_file_validate("16016","修改流程文件，流程id和流程文件不能为空！" ),
    delete_not_draft("16017", "非草稿状态不能删除！"),
    start_is_on("16018","流程已是激活状态。" ),
    start_file_illegality("16019", "流程文件非法！"),
    start_file_illegality1("16019", "流程文件无法转换为BpmnModel"),
    start_file_illegality2("16019", "未找到processes"),
    start_file_illegality3("16019", "%s节点未设置处理人！"),
    start_file_illegality4("16019", "%s节点用户任务多人或签人数过多！"),
    start_file_illegality5("16019", "%s节点未设置审批角色"),
    start_file_illegality6("16019", "流程结束节点不能为空！"),

    PROCESS_DEFINITION_EMPTY("16020","流程模板为空，请联系管理员核查" ),
    PROCESS_INSTANCE_EXIST("16021", "流程实例已存在。"),
    PROCESS_INSTANCE_NOT_EXIST("1000", "流程实例不存在"),
    PROCESS_INSTANCE_ADD_FAIL("16022","添加流程失败！" ),
    PROCESS_DEFINITION_NOT_ACTIVE("16023","流程未启用，请联系管理员核查" ),
    PROCESS_DEFINITION_EXIST_PROCESS_TYPE("16024","已经存在同类型的流程实例！" ),
    PROCESS_TASK_NOT_EXIST("16025","流程任务不存在！" ),
    PROCESS_TASK_EXECUTE_NOT_FOUND("16026","未找到对应类型的流程实现！" ),
    PROCESS_TASK_USER_NOT_FOUND_TASK("16027","当前登录用户未找到当前节点的任务" ),
    FORM_INFO__NOT_FOUND("16028","未找到对应的form表单配置" ),
    INSTANCE_ID_IS_EMPTY("1001","查询实例条件instanceId为空！" );

    WorkflowApplicationEnum(String status, String message) {
        this.status = status;
        this.message = message;
    }

    private String status;
    private String message;

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
