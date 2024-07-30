
### 项目表结构
```sql
DROP TABLE IF EXISTS `t_pm_domain_role_user`;
CREATE TABLE `t_pm_domain_role_user` (
 `domain_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '空间id',
 `role_key` varchar(20) NOT NULL COMMENT '角色ID',
 `user` varchar(30) NOT NULL COMMENT '用户',
 `user_name` varchar(30) NOT NULL COMMENT '用户名称',
 `user_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '用户类型,0角色普通用户，1管理用户',
 PRIMARY KEY (`domain_id`,`role_key`,`user`,`user_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='空间角色关联用户表';



DROP TABLE IF EXISTS `t_pm_project_role_user`;
CREATE TABLE `t_pm_project_role_user` (
`domain_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '空间id',
`pm_id` bigint(20) NOT NULL COMMENT '项目id',
`role_key` varchar(20) NOT NULL COMMENT '角色key',
`user` varchar(30) NOT NULL COMMENT '用户名称',
`user_name` varchar(30) NOT NULL COMMENT '用户名称',
`user_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '用户类型,0角色普通用户，1管理用户',
PRIMARY KEY (`pm_id`,`role_key`,`user`,`user_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='项目角色关联用户表';

DROP TABLE IF EXISTS `t_pm_project_plan`;
CREATE TABLE `t_pm_project_plan` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
 `pm_id` bigint(20) NOT NULL COMMENT '项目id',
 `node_id` bigint(20) NOT NULL COMMENT '节点id',
 `task_key` varchar(20) NOT NULL COMMENT '任务key，需要分类',
 `task_name` varchar(30) DEFAULT NULL COMMENT '任务名称',
 `plan_start_time` datetime DEFAULT NULL COMMENT '计划开始时间',
 `plan_end_time` datetime DEFAULT NULL COMMENT '计划结束时间',
 `plan_duration` int(11) NOT NULL DEFAULT '4' COMMENT '计划耗时，小时',
 `plan_time_unit` varchar(12) DEFAULT NULL COMMENT '计划时间单位',
 `leader` varchar(50) DEFAULT NULL COMMENT '负责人',
 `domain_id` bigint(20) NOT NULL COMMENT '空间id',
 `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态:0 未排 1 已排',
 `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
 `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_by` varchar(20) DEFAULT NULL COMMENT '更新者',
 `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 `remark` varchar(200) DEFAULT NULL COMMENT '描述',
 PRIMARY KEY (`id`) USING BTREE,
 KEY `idx_domain_id` (`domain_id`),
 KEY `idx_pm_id` (`pm_id`),
 KEY `idx_node_id` (`node_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='项目排期';
DROP TABLE IF EXISTS `t_pm_project`;
CREATE TABLE `t_pm_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `biz_no` varchar(20) DEFAULT NULL COMMENT '业务编号',
  `name` varchar(50) DEFAULT NULL COMMENT '需求名称',
  `story` varchar(200) DEFAULT NULL COMMENT '需求故事',
  `requirement_url` varchar(100) DEFAULT NULL COMMENT '需求wiki地址',
  `ui_url` varchar(100) DEFAULT NULL COMMENT 'ui地址',
  `design_url` varchar(100) DEFAULT NULL COMMENT '开发设计地址',
  `case_url` varchar(100) DEFAULT NULL COMMENT '用例地址',
  `type` varchar(20) DEFAULT NULL COMMENT '需求类型，流程编号',
  `priority` varchar(2) DEFAULT NULL COMMENT '优先级，P0,P1,P2,P3',
  `eap` varchar(10) DEFAULT NULL COMMENT '预计发布版本',
  `ga` varchar(10) DEFAULT NULL COMMENT '实际发布版本',
  `expected_time` timestamp NULL DEFAULT NULL COMMENT '期望时间',
  `plan_time` timestamp NULL DEFAULT NULL COMMENT '计划排期',
  `dead_line` timestamp NULL DEFAULT NULL COMMENT '截止时间',
  `actual_time` timestamp NULL DEFAULT NULL COMMENT '实际时间',
  `current_node` varchar(32) DEFAULT NULL COMMENT '当前节点',
  `current_name` varchar(32) DEFAULT NULL COMMENT '当前节点中文名',
  `group_id` varchar(32) DEFAULT NULL COMMENT '群id',
  `group_type` varchar(10) DEFAULT NULL COMMENT '群类型,dingtalk,feishu',
  `lob_id` bigint(20) NOT NULL COMMENT '所属业务线',
  `domain_id` bigint(20) NOT NULL COMMENT '空间id',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '需求状态,枚举：-1异常，0初始化，1运行中，2挂起，3完成，4取消',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_domain_id` (`domain_id`),
  KEY `idx_lob_id` (`lob_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='项目（需求）信息';
-- ----------------------------
-- Table structure for t_pm_domain
-- ----------------------------
DROP TABLE IF EXISTS `t_pm_domain`;
CREATE TABLE `t_pm_domain` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(50) DEFAULT NULL COMMENT '空间名称',
  `description` varchar(200) DEFAULT NULL COMMENT '空间描述',
  `domain` varchar(50) DEFAULT NULL COMMENT '空间域名',
  `icon` varchar(200) DEFAULT NULL COMMENT '空间图标',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户id',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态:0 禁用 1 启用',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='项目空间（租户的概念）';

-- ----------------------------
-- Table structure for t_pm_domain_lob
-- ----------------------------
DROP TABLE IF EXISTS `t_pm_domain_lob`;
CREATE TABLE `t_pm_domain_lob` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(30) NOT NULL COMMENT '业务线名称',
  `description` varchar(100) DEFAULT NULL COMMENT '业务线描述',
  `admin` varchar(100) DEFAULT NULL COMMENT '管理员',
  `sort` tinyint(11) DEFAULT NULL COMMENT '显示顺序',
  `domain_id` bigint(20) NOT NULL COMMENT '空间id',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态:0 禁用 1 启用',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_domain_id` (`domain_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='空间对应的业务线';

-- ----------------------------
-- Table structure for t_pm_domain_role
-- ----------------------------
DROP TABLE IF EXISTS `t_pm_domain_role`;
CREATE TABLE `t_pm_domain_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(20) DEFAULT NULL COMMENT '角色标识',
  `default_users` varchar(100) DEFAULT NULL COMMENT '默认用户',
  `default_leader` varchar(30) DEFAULT NULL COMMENT '默认leader',
  `sort` tinyint(11) DEFAULT NULL COMMENT '显示顺序',
  `domain_id` bigint(20) NOT NULL COMMENT '空间id,为0表示通用',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态:0 禁用 1 启用',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_domain_id` (`domain_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='空间预定义角色';

-- ----------------------------
-- Table structure for t_pm_project_role
-- ----------------------------
DROP TABLE IF EXISTS `t_pm_project_role`;
CREATE TABLE `t_pm_project_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pm_id` bigint(20) NOT NULL COMMENT '项目id',
  `role_name` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `role_key` varchar(20) NOT NULL COMMENT '角色标识',
  `users` varchar(200) DEFAULT NULL COMMENT '用户逗号分隔',
  `leader` varchar(20) DEFAULT NULL COMMENT '角色负责人',
  `domain_id` bigint(20) NOT NULL COMMENT '空间id',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态:0 禁用 1 启用',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_domain_id` (`domain_id`),
  KEY `idx_pm_id` (`pm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='项目角色人员';

-- ----------------------------
-- Table structure for t_pm_project_node_task
-- ----------------------------
DROP TABLE IF EXISTS `t_pm_project_node_task`;
CREATE TABLE `t_pm_project_node_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pm_id` bigint(20) NOT NULL COMMENT '项目id',
  `node_id` bigint(20) NOT NULL COMMENT '节点id',
  `task_key` varchar(20) NOT NULL COMMENT '任务key，需要分类',
  `task_type` tinyint(4) DEFAULT '1' COMMENT '任务类型：0，内置；1用户',
  `task_name` varchar(30) DEFAULT NULL COMMENT '任务名称',
  `delivery` varchar(100) DEFAULT NULL COMMENT '任务交付',
  `plan_start_time` datetime DEFAULT NULL COMMENT '计划开始时间',
  `plan_end_time` datetime DEFAULT NULL COMMENT '计划结束时间',
  `plan_duration` int(11) DEFAULT '4' COMMENT '计划耗时，小时',
  `plan_time_unit` varchar(12) DEFAULT NULL COMMENT '计划时间单位',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '任务开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '任务结束时间',
  `actual_duration` int(11) DEFAULT '4' COMMENT '实际耗时，小时',
  `time_unit` varchar(12) DEFAULT NULL COMMENT '时间单位',
  `leader` varchar(50) DEFAULT NULL COMMENT '负责人',
  `domain_id` bigint(20) NOT NULL COMMENT '空间id',
  `lob_id` bigint(20) DEFAULT NULL COMMENT '业务线id',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态:0 未完成 1 已完成',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_domain_id` (`domain_id`),
  KEY `idx_pm_id` (`pm_id`),
  KEY `idx_leader` (`leader`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='节点任务';

-- ----------------------------
-- Table structure for t_pm_project_node
-- ----------------------------
DROP TABLE IF EXISTS `t_pm_project_node`;
CREATE TABLE `t_pm_project_node` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
 `pm_id` bigint(20) NOT NULL COMMENT '项目id',
 `node_key` varchar(30) NOT NULL COMMENT '节点key',
 `node_name` varchar(30) NOT NULL COMMENT '节点名称',
 `roles` varchar(50) DEFAULT NULL COMMENT '角色',
 `main_role` varchar(20) DEFAULT NULL COMMENT '主角色',
 `plan_start_time` datetime DEFAULT NULL COMMENT '计划开始时间',
 `plan_end_time` datetime DEFAULT NULL COMMENT '计划结束时间',
 `plan_duration` int(11) DEFAULT '4' COMMENT '计划耗时，小时',
 `plan_time_unit` varchar(12) DEFAULT NULL COMMENT '计划时间单位',
 `start_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
 `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间',
 `total_duration` int(11) DEFAULT '4' COMMENT '总耗时，小时',
 `time_unit` varchar(12) DEFAULT NULL COMMENT '时间单位',
 `leader` varchar(50) DEFAULT NULL COMMENT '负责人',
 `domain_id` bigint(20) DEFAULT NULL COMMENT '空间id',
 `lob_id` bigint(20) DEFAULT NULL COMMENT '业务线id',
 `status` tinyint(4) DEFAULT '0' COMMENT '状态:0 禁用 1 启用',
 `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
 `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_by` varchar(20) DEFAULT NULL COMMENT '更新者',
 `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 `remark` varchar(200) DEFAULT NULL COMMENT '描述',
 PRIMARY KEY (`id`) USING BTREE,
 UNIQUE KEY `uniq_pm_node_key` (`pm_id`,`node_key`) USING BTREE,
 KEY `idx_domain_id` (`domain_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='项目节点';
```

### 初始化数据
```sql
-- 初始化字典
INSERT INTO `t_sys_dict_type` (`id`, `name`, `classify`, `type`, `char_type`, `tenant_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, '任务类型', 1, 'pm_task_type', 'str', 1001, 1, 'yxkong', '2023-12-08 12:27:42', '', '2023-12-08 12:27:42', NULL);
INSERT INTO `t_sys_dict_type` (`id`, `name`, `classify`, `type`, `char_type`, `tenant_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, '排期节点', 1, 'pm_schedule_node', 'str', 1001, 1, 'yxkong', '2023-12-20 16:04:41', '', '2023-12-20 16:04:41', '项目排期的节点');

INSERT INTO `t_sys_dict` (`id`, `key`, `label`, `dict_type`, `css_class`, `list_class`, `sort`, `tenant_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, 'sys_tenant', '租户', 'sys_gen_code_dict', NULL, 'default', 1, 1001, 1, 'yxkong', '2023-11-20 11:08:10', '', '2023-11-20 11:08:10', NULL);
INSERT INTO `t_sys_dict` (`id`, `key`, `label`, `dict_type`, `css_class`, `list_class`, `sort`, `tenant_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, 'dev', '开发', 'pm_task_type', NULL, 'success', 5, 1001, 1, 'yxkong', '2023-12-08 12:34:28', 'dingchuanqing', '2023-12-20 17:43:38', NULL);
INSERT INTO `t_sys_dict` (`id`, `key`, `label`, `dict_type`, `css_class`, `list_class`, `sort`, `tenant_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, 'design', '设计', 'pm_task_type', NULL, 'success', 4, 1001, 1, 'yxkong', '2023-12-08 12:34:40', 'dingchuanqing', '2023-12-20 17:43:41', NULL);
INSERT INTO `t_sys_dict` (`id`, `key`, `label`, `dict_type`, `css_class`, `list_class`, `sort`, `tenant_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, 'support', '支持', 'pm_task_type', NULL, 'danger', 1, 1001, 1, 'yxkong', '2023-12-08 12:35:15', '', '2023-12-08 12:35:15', NULL);
INSERT INTO `t_sys_dict` (`id`, `key`, `label`, `dict_type`, `css_class`, `list_class`, `sort`, `tenant_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, 'test', '测试', 'pm_task_type', NULL, 'success', 7, 1001, 1, 'yxkong', '2023-12-08 12:35:27', 'dingchuanqing', '2023-12-20 17:43:31', NULL);
INSERT INTO `t_sys_dict` (`id`, `key`, `label`, `dict_type`, `css_class`, `list_class`, `sort`, `tenant_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, 'fedDev', '前端排期', 'pm_schedule_node', NULL, 'default', 1, 1001, 1, 'yxkong', '2023-12-20 16:35:59', '', '2023-12-20 16:35:59', NULL);
INSERT INTO `t_sys_dict` (`id`, `key`, `label`, `dict_type`, `css_class`, `list_class`, `sort`, `tenant_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, 'bedDev', '后端排期', 'pm_schedule_node', NULL, 'default', 1, 1001, 1, 'yxkong', '2023-12-20 16:36:14', '', '2023-12-20 16:36:14', NULL);
INSERT INTO `t_sys_dict` (`id`, `key`, `label`, `dict_type`, `css_class`, `list_class`, `sort`, `tenant_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, 'qaTest', '测试排期', 'pm_schedule_node', NULL, 'danger', 1, 1001, 1, 'yxkong', '2023-12-20 16:36:40', '', '2023-12-20 16:36:40', NULL);
INSERT INTO `t_sys_dict` (`id`, `key`, `label`, `dict_type`, `css_class`, `list_class`, `sort`, `tenant_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, 'jointDebug', '联调排期', 'pm_schedule_node', NULL, 'default', 1, 1001, 1, 'yxkong', '2023-12-20 16:36:54', '', '2023-12-20 16:36:54', NULL);
INSERT INTO `t_sys_dict` (`id`, `key`, `label`, `dict_type`, `css_class`, `list_class`, `sort`, `tenant_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, 'qaCaseTest', '测试用例排期', 'pm_schedule_node', 'default', 'default', 1, 1001, 1, 'dingchuanqing', '2023-12-20 17:31:34', 'dingchuanqing', '2023-12-20 17:32:24', NULL);
INSERT INTO `t_sys_dict` (`id`, `key`, `label`, `dict_type`, `css_class`, `list_class`, `sort`, `tenant_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, 'assess', '评审会', 'pm_task_type', NULL, 'default', 3, 1001, 1, 'dingchuanqing', '2023-12-20 17:35:14', 'dingchuanqing', '2023-12-20 17:43:45', NULL);
INSERT INTO `t_sys_dict` (`id`, `key`, `label`, `dict_type`, `css_class`, `list_class`, `sort`, `tenant_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (NULL, 'intergratedTest', '联调', 'pm_task_type', NULL, 'default', 6, 1001, 1, 'dingchuanqing', '2023-12-20 17:37:53', 'dingchuanqing', '2023-12-20 17:43:34', NULL);
INSERT INTO `t_sys_dict` (`id`, `key`, `label`, `dict_type`, `css_class`, `list_class`, `sort`, `tenant_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (NULL, 'writeDoc', '编写文档', 'pm_task_type', NULL, 'default', 2, 1001, 1, 'dingchuanqing', '2023-12-20 17:40:14', '', '2023-12-20 17:40:14', NULL);
INSERT INTO `t_sys_dict` (`id`, `key`, `label`, `dict_type`, `css_class`, `list_class`, `sort`, `tenant_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (NULL, 'checkAccept', '业务验收', 'pm_task_type', NULL, 'default', 8, 1001, 1, 'dingchuanqing', '2023-12-20 17:45:30', '', '2023-12-20 17:45:30', NULL);
INSERT INTO `t_sys_dict` (`id`, `key`, `label`, `dict_type`, `css_class`, `list_class`, `sort`, `tenant_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (NULL, 'online', '上线', 'pm_task_type', NULL, 'default', 9, 1001, 1, 'dingchuanqing', '2023-12-20 17:46:31', '', '2023-12-20 17:46:31', NULL);

-- 全流程工作流
INSERT INTO `t_flw_process_definition` (
    `id`,
    `process_no`,
    `process_name`,
    `process_type`,
    `process_desc`,
    `deploy_id`,
    `process_version`,
    `process_file`,
    `form_no`,
    `tenant_id`,
    `dept_id`,
    `deleted`,
    `status`,
    `create_time`,
    `update_time`,
    `update_by`,
    `create_by`,
    `remark`
)
VALUES
    (
        null,
        'P2312210000',
        '全流程',
        'pm',
        NULL,
        NULL,
        1,
        '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:flowable=\"http://flowable.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:bpmn2=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\" expressionLanguage=\"http://www.w3.org/1999/XPath\" targetNamespace=\"http://flowable.org/bpmn\" id=\"diagram_Process_1699944087252\" xsi:schemaLocation=\"http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd\">\n  <process id=\"P2311300000\" name=\"全流程\" isExecutable=\"true\">\n    <extensionElements>\n      <flowable:properties></flowable:properties>\n    </extensionElements>\n    <startEvent id=\"start\" name=\"需求填报\" flowable:formKey=\"key_FNO23111900001\"></startEvent>\n    <userTask id=\"demandAnalysis\" name=\"需求分析\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"PM\" flowable:formKey=\"key_FNO23111900001\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"产品经理\" flowable:dataType=\"ROLES\">\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_1nzv519\" sourceRef=\"start\" targetRef=\"demandAnalysis\"></sequenceFlow>\n    <userTask id=\"srr\" name=\"需求评审\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"PMO,PM,UI,FED,BED,QA,SA,TD,TL\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"产品经理,项目经理,UI设计,前端开发,后端开发,测试,架构师,技术负责人,团队负责人\">\n      <extensionElements>\n        <flowable:properties>\n          <flowable:property name=\"owner\" value=\"PMO\"></flowable:property>\n        </flowable:properties>\n      </extensionElements>\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_1f9m8ly\" sourceRef=\"demandAnalysis\" targetRef=\"srr\"></sequenceFlow>\n    <userTask id=\"softDesign\" name=\"技术设计\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"FED,BED,SA,TD\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"前端开发,后端开发,架构师,技术负责人\">\n      <extensionElements>\n        <flowable:properties>\n          <flowable:property name=\"owner\" value=\"TD\"></flowable:property>\n        </flowable:properties>\n      </extensionElements>\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <userTask id=\"designReview\" name=\"设计评审\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"PM,PMO,UI,FED,BED,QA,SA,TD,TL\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"产品经理,项目经理,UI设计,前端开发,后端开发,测试,架构师,技术负责人,团队负责人\">\n      <extensionElements>\n        <flowable:executionListener event=\"end\" class=\"com.github.platform.core.pm.infra.listener.PmSplitConditionListener\"></flowable:executionListener>\n        <flowable:properties>\n          <flowable:property name=\"owner\" value=\"TD\"></flowable:property>\n        </flowable:properties>\n      </extensionElements>\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_0ssrn24\" sourceRef=\"softDesign\" targetRef=\"designReview\"></sequenceFlow>\n    <userTask id=\"fedDev\" name=\"前端开发\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"FED\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"前端开发\">\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <userTask id=\"qaCase\" name=\"用例编写\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"QA\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"测试\">\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <userTask id=\"caseReview\" name=\"用例评审\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"QA,FED,BED\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"前端开发,后端开发,测试\">\n      <extensionElements>\n        <flowable:properties>\n          <flowable:property name=\"owner\" value=\"QA\"></flowable:property>\n        </flowable:properties>\n      </extensionElements>\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_02j1ci7\" sourceRef=\"qaCase\" targetRef=\"caseReview\"></sequenceFlow>\n    <userTask id=\"qaTest\" name=\"测试\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"QA,FED,BED\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"前端开发,后端开发,测试\">\n      <extensionElements>\n        <flowable:properties>\n          <flowable:property name=\"owner\" value=\"QA\"></flowable:property>\n        </flowable:properties>\n      </extensionElements>\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <userTask id=\"preCheck\" name=\"预验收\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"UI,PM\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"产品经理,UI设计\">\n      <extensionElements>\n        <flowable:properties>\n          <flowable:property name=\"owner\" value=\"PM\"></flowable:property>\n        </flowable:properties>\n      </extensionElements>\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_0x73fal\" sourceRef=\"qaTest\" targetRef=\"preCheck\"></sequenceFlow>\n    <userTask id=\"online\" name=\"上线\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"FED,BED,TD,QA,PMO\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"项目经理,前端开发,后端开发,测试,技术负责人\">\n      <extensionElements>\n        <flowable:properties>\n          <flowable:property name=\"owner\" value=\"TD\"></flowable:property>\n        </flowable:properties>\n      </extensionElements>\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_07h9c2e\" sourceRef=\"preCheck\" targetRef=\"online\"></sequenceFlow>\n    <endEvent id=\"end\" name=\"完结\"></endEvent>\n    <sequenceFlow id=\"Flow_0a8rw0s\" sourceRef=\"pmCheck\" targetRef=\"end\"></sequenceFlow>\n    <userTask id=\"bedDev\" name=\"后端开发\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"BED\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"后端开发\">\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_06nkr92\" sourceRef=\"designReview\" targetRef=\"bedDev\">\n      <conditionExpression xsi:type=\"tFormalExpression\">${hasBED}</conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow id=\"Flow_0el54ur\" sourceRef=\"designReview\" targetRef=\"fedDev\">\n      <conditionExpression xsi:type=\"tFormalExpression\">${hasFED}</conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow id=\"Flow_1lqq0ny\" sourceRef=\"designReview\" targetRef=\"qaCase\">\n      <conditionExpression xsi:type=\"tFormalExpression\">${hasQA}</conditionExpression>\n    </sequenceFlow>\n    <userTask id=\"pmCheck\" name=\"产品验收\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"PM\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"产品经理\">\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_0lb98w1\" sourceRef=\"online\" targetRef=\"pmCheck\"></sequenceFlow>\n    <userTask id=\"jointDebug\" name=\"联调\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"TD\" flowable:formKey=\"key_FNO23111900001\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:localScope=\"true\" flowable:assigneeType=\"ROLES\" flowable:text=\"技术负责人\">\n      <extensionElements>\n        <flowable:properties>\n          <flowable:property name=\"owner\" value=\"TD\"></flowable:property>\n        </flowable:properties>\n      </extensionElements>\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_0scubyn\" name=\"提测\" sourceRef=\"jointDebug\" targetRef=\"qaTest\"></sequenceFlow>\n    <sequenceFlow id=\"Flow_1mncygv\" sourceRef=\"bedDev\" targetRef=\"Gateway_068n941\"></sequenceFlow>\n    <sequenceFlow id=\"Flow_11wzaac\" sourceRef=\"fedDev\" targetRef=\"Gateway_068n941\"></sequenceFlow>\n    <sequenceFlow id=\"Flow_1u66wiv\" sourceRef=\"caseReview\" targetRef=\"Gateway_068n941\"></sequenceFlow>\n    <sequenceFlow id=\"Flow_1mn9llr\" sourceRef=\"Gateway_068n941\" targetRef=\"jointDebug\"></sequenceFlow>\n    <parallelGateway id=\"Gateway_068n941\"></parallelGateway>\n    <userTask id=\"planSchedule\" name=\"预排期\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"PMO,UI,FED,BED,QA,TD\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"项目经理,UI设计,前端开发,后端开发,测试,技术负责人\">\n      <extensionElements>\n        <flowable:properties>\n          <flowable:property name=\"owner\" value=\"PMO\"></flowable:property>\n        </flowable:properties>\n      </extensionElements>\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_1g7ggjh\" sourceRef=\"srr\" targetRef=\"planSchedule\"></sequenceFlow>\n    <sequenceFlow id=\"Flow_1ftzke4\" sourceRef=\"planSchedule\" targetRef=\"softDesign\"></sequenceFlow>\n  </process>\n  <bpmndi:BPMNDiagram id=\"BPMNDiagram_P2311300000\">\n    <bpmndi:BPMNPlane bpmnElement=\"P2311300000\" id=\"BPMNPlane_P2311300000\">\n      <bpmndi:BPMNShape bpmnElement=\"start\" id=\"BPMNShape_start\">\n        <omgdc:Bounds height=\"36.0\" width=\"36.0\" x=\"392.0\" y=\"272.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"demandAnalysis\" id=\"BPMNShape_demandAnalysis\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"470.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"srr\" id=\"BPMNShape_srr\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"610.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"softDesign\" id=\"BPMNShape_softDesign\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"890.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"designReview\" id=\"BPMNShape_designReview\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"1020.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"fedDev\" id=\"BPMNShape_fedDev\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"1170.0\" y=\"140.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"qaCase\" id=\"BPMNShape_qaCase\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"1170.0\" y=\"360.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"caseReview\" id=\"BPMNShape_caseReview\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"1350.0\" y=\"360.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"qaTest\" id=\"BPMNShape_qaTest\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"1630.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"preCheck\" id=\"BPMNShape_preCheck\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"1790.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"online\" id=\"BPMNShape_online\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"1930.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"end\" id=\"BPMNShape_end\">\n        <omgdc:Bounds height=\"36.0\" width=\"36.0\" x=\"2212.0\" y=\"272.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"bedDev\" id=\"BPMNShape_bedDev\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"1170.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"pmCheck\" id=\"BPMNShape_pmCheck\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"2070.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"jointDebug\" id=\"BPMNShape_jointDebug\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"1470.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"Gateway_068n941\" id=\"BPMNShape_Gateway_068n941\">\n        <omgdc:Bounds height=\"50.0\" width=\"50.0\" x=\"1375.0\" y=\"265.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"planSchedule\" id=\"BPMNShape_planSchedule\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"750.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_1nzv519\" id=\"BPMNEdge_Flow_1nzv519\">\n        <omgdi:waypoint x=\"428.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"470.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_1f9m8ly\" id=\"BPMNEdge_Flow_1f9m8ly\">\n        <omgdi:waypoint x=\"570.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"610.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_0ssrn24\" id=\"BPMNEdge_Flow_0ssrn24\">\n        <omgdi:waypoint x=\"990.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1020.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_02j1ci7\" id=\"BPMNEdge_Flow_02j1ci7\">\n        <omgdi:waypoint x=\"1270.0\" y=\"400.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1350.0\" y=\"400.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_0x73fal\" id=\"BPMNEdge_Flow_0x73fal\">\n        <omgdi:waypoint x=\"1730.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1790.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_07h9c2e\" id=\"BPMNEdge_Flow_07h9c2e\">\n        <omgdi:waypoint x=\"1890.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1930.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_0a8rw0s\" id=\"BPMNEdge_Flow_0a8rw0s\">\n        <omgdi:waypoint x=\"2170.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"2212.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_06nkr92\" id=\"BPMNEdge_Flow_06nkr92\">\n        <omgdi:waypoint x=\"1120.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1170.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_0el54ur\" id=\"BPMNEdge_Flow_0el54ur\">\n        <omgdi:waypoint x=\"1070.0\" y=\"250.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1070.0\" y=\"180.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1170.0\" y=\"180.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_1lqq0ny\" id=\"BPMNEdge_Flow_1lqq0ny\">\n        <omgdi:waypoint x=\"1070.0\" y=\"330.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1070.0\" y=\"400.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1170.0\" y=\"400.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_0lb98w1\" id=\"BPMNEdge_Flow_0lb98w1\">\n        <omgdi:waypoint x=\"2030.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"2070.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_0scubyn\" id=\"BPMNEdge_Flow_0scubyn\">\n        <omgdi:waypoint x=\"1570.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1630.0\" y=\"290.0\"></omgdi:waypoint>\n        <bpmndi:BPMNLabel>\n          <omgdc:Bounds height=\"14.0\" width=\"22.0\" x=\"1589.0\" y=\"272.0\"></omgdc:Bounds>\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_1mncygv\" id=\"BPMNEdge_Flow_1mncygv\">\n        <omgdi:waypoint x=\"1270.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1375.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_11wzaac\" id=\"BPMNEdge_Flow_11wzaac\">\n        <omgdi:waypoint x=\"1270.0\" y=\"180.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1400.0\" y=\"180.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1400.0\" y=\"265.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_1u66wiv\" id=\"BPMNEdge_Flow_1u66wiv\">\n        <omgdi:waypoint x=\"1400.0\" y=\"360.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1400.0\" y=\"315.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_1mn9llr\" id=\"BPMNEdge_Flow_1mn9llr\">\n        <omgdi:waypoint x=\"1425.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1470.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_1g7ggjh\" id=\"BPMNEdge_Flow_1g7ggjh\">\n        <omgdi:waypoint x=\"710.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"750.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_1ftzke4\" id=\"BPMNEdge_Flow_1ftzke4\">\n        <omgdi:waypoint x=\"850.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"890.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n    </bpmndi:BPMNPlane>\n  </bpmndi:BPMNDiagram>\n</definitions>',
        NULL,
        1001,
        1,
        0,
        0,
        '2023-12-21 16:35:32',
        null,
        'yxkong',
        'yxkong',
        NULL
    );
-- 流程表达式
INSERT INTO `t_flw_process_condition` (`id`, `name`, `expression`, `process_type`, `status`, `create_time`, `update_time`, `update_by`, `create_by`, `remark`) VALUES (1, '有前端', '${hasFED}', 'pm', 1, '2023-11-30 19:29:41', '2023-12-18 18:58:26', 'yxkong', 'yxkong', NULL);
INSERT INTO `t_flw_process_condition` (`id`, `name`, `expression`, `process_type`, `status`, `create_time`, `update_time`, `update_by`, `create_by`, `remark`) VALUES (2, '有后端', '${hasBED}', 'pm', 1, '2023-12-04 16:42:50', '2023-12-18 18:58:36', 'yxkong', 'yxkong', NULL);
INSERT INTO `t_flw_process_condition` (`id`, `name`, `expression`, `process_type`, `status`, `create_time`, `update_time`, `update_by`, `create_by`, `remark`) VALUES (3, '有测试', '${hasQA}', 'pm', 1, '2023-12-04 16:43:07', '2023-12-18 18:58:43', 'yxkong', 'yxkong', NULL);

-- 项目流程默认角色
INSERT INTO `t_pm_domain_role` (`id`, `role_name`, `role_key`, `default_users`, `default_leader`, `sort`, `domain_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1, '产品经理', 'PM', NULL, NULL, 1, 0, 1, 'init', '2023-11-21 19:10:14', 'yxkong', '2023-12-18 18:42:18', 'Product Manager');
INSERT INTO `t_pm_domain_role` (`id`, `role_name`, `role_key`, `default_users`, `default_leader`, `sort`, `domain_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2, '项目经理', 'PMO', NULL, NULL, 2, 0, 1, 'init', '2023-11-21 19:10:39', 'yxkong', '2023-12-18 18:42:40', 'Project Manager');
INSERT INTO `t_pm_domain_role` (`id`, `role_name`, `role_key`, `default_users`, `default_leader`, `sort`, `domain_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (3, 'UI设计', 'UI', NULL, NULL, 3, 0, 1, 'init', '2023-11-21 19:10:39', 'yxkong', '2023-12-19 11:14:24', 'UI Designer');
INSERT INTO `t_pm_domain_role` (`id`, `role_name`, `role_key`, `default_users`, `default_leader`, `sort`, `domain_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (4, '前端开发', 'FED', NULL, NULL, 4, 0, 1, 'init', '2023-11-21 19:10:39', 'yxkong', '2023-12-19 18:55:51', 'Frontend Developer');
INSERT INTO `t_pm_domain_role` (`id`, `role_name`, `role_key`, `default_users`, `default_leader`, `sort`, `domain_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (5, '后端开发', 'BED', NULL, NULL, 4, 0, 1, 'init', '2023-11-21 19:10:39', NULL, '2023-12-12 14:20:32', 'Backend Developer');
INSERT INTO `t_pm_domain_role` (`id`, `role_name`, `role_key`, `default_users`, `default_leader`, `sort`, `domain_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (6, '测试', 'QA', NULL, NULL, 4, 0, 1, 'init', '2023-11-21 19:10:39', 'yxkong', '2023-12-19 18:56:21', 'Quality Assurance');
INSERT INTO `t_pm_domain_role` (`id`, `role_name`, `role_key`, `default_users`, `default_leader`, `sort`, `domain_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (7, '测试负责人', 'QAL', NULL, NULL, 4, 0, 0, 'init', '2023-11-21 19:10:39', NULL, '2023-12-18 11:17:02', 'Test Lead');
INSERT INTO `t_pm_domain_role` (`id`, `role_name`, `role_key`, `default_users`, `default_leader`, `sort`, `domain_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (8, '前端负责人', 'FEL', NULL, NULL, 4, 0, 0, 'init', '2023-11-21 19:10:39', NULL, '2023-12-18 11:17:02', 'Frontend Lead');
INSERT INTO `t_pm_domain_role` (`id`, `role_name`, `role_key`, `default_users`, `default_leader`, `sort`, `domain_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (9, '后端负责人', 'BEL', NULL, NULL, 4, 0, 0, 'init', '2023-11-21 19:10:39', NULL, '2023-12-18 11:17:02', 'Backend Lead');
INSERT INTO `t_pm_domain_role` (`id`, `role_name`, `role_key`, `default_users`, `default_leader`, `sort`, `domain_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (10, '架构师', 'SA', NULL, NULL, 4, 0, 1, 'init', '2023-11-21 19:10:39', NULL, '2023-12-12 14:20:32', 'Solution Architect');
INSERT INTO `t_pm_domain_role` (`id`, `role_name`, `role_key`, `default_users`, `default_leader`, `sort`, `domain_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (11, '技术负责人', 'TD', NULL, NULL, 4, 0, 1, 'init', '2023-12-12 14:19:31', NULL, '2023-12-12 14:24:05', 'Technical Director');
INSERT INTO `t_pm_domain_role` (`id`, `role_name`, `role_key`, `default_users`, `default_leader`, `sort`, `domain_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (12, '团队负责人', 'TL', NULL, NULL, 5, 0, 1, 'init', '2023-12-12 14:24:30', NULL, '2023-12-18 13:50:18', 'Team Leader');


-- 初始化空间 和业务线
INSERT INTO `t_pm_domain` (`id`, `name`, `description`, `domain`, `icon`, `tenant_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1, '测试项目', '测试项目空间', NULL, NULL, 1001, 1, 'yxkong', '2023-11-21 11:15:31', NULL, '2023-11-21 11:15:31', NULL);
INSERT INTO `t_pm_domain_lob` (`id`, `name`, `description`, `admin`, `sort`, `domain_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1, '渠道', 'xy', 'yxkong', 1, 1, 0, NULL, '2023-11-23 12:42:13', 'yxkong', '2023-12-15 13:55:35', NULL);
INSERT INTO `t_pm_domain_lob` (`id`, `name`, `description`, `admin`, `sort`, `domain_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2, '商城', '1', 'yxkong', 1, 1, 0, NULL, '2023-11-23 12:42:30', 'yxkong', '2023-12-18 16:00:22', NULL);

```


