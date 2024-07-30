### 表结构
```sql

```

### 初始化数据
#### 自定义流程表
```sql
DROP TABLE IF EXISTS `t_flw_form_data`;
CREATE TABLE `t_flw_form_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `form_no` varchar(50) DEFAULT NULL COMMENT '表单编号',
  `instance_no` varchar(50) DEFAULT NULL COMMENT '实例编号',
  `type` varchar(50) DEFAULT 'input' COMMENT '输入框类型 input、textare、select、radio、upload',
  `label` varchar(50) DEFAULT NULL COMMENT '中文显示',
  `name` varchar(50) DEFAULT NULL COMMENT '字段名称',
  `value` varchar(300) DEFAULT NULL COMMENT '数据值',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '更新者',
  `create_by` varchar(30) DEFAULT NULL COMMENT '审批流发起人',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='表单数据';

-- ----------------------------
-- Table structure for t_flw_form
-- ----------------------------
DROP TABLE IF EXISTS `t_flw_form`;
CREATE TABLE `t_flw_form` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `form_no` varchar(50) DEFAULT NULL COMMENT '表单编号',
  `form_name` varchar(50) DEFAULT NULL COMMENT '表单名称',
  `form_desc` varchar(200) DEFAULT NULL COMMENT '表单描述',
  `form_channel` varchar(10) DEFAULT 'system' COMMENT '渠道,system,dingding,feishu',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态:0 禁用 1 启用',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '更新者',
  `create_by` varchar(30) DEFAULT NULL COMMENT '审批流发起人',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='表单定义';

-- ----------------------------
-- Table structure for t_flw_form_info
-- ----------------------------
DROP TABLE IF EXISTS `t_flw_form_info`;
CREATE TABLE `t_flw_form_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `form_no` varchar(50) DEFAULT NULL COMMENT '表单编号',
  `type` varchar(50) DEFAULT 'input' COMMENT '输入框类型 input、textarea、select、radio、upload',
  `label` varchar(50) DEFAULT NULL COMMENT '中文名',
  `name` varchar(50) DEFAULT NULL COMMENT '字段名',
  `len` int(11) DEFAULT '100' COMMENT '数据长度，最大300',
  `required` tinyint(2) DEFAULT '0' COMMENT '是否必填，0非必填，1必填',
  `sort` tinyint(4) DEFAULT '0' COMMENT '排序',
  `default_val` varchar(50) DEFAULT NULL COMMENT '默认值',
  `option_name` varchar(50) DEFAULT NULL COMMENT '配置项名称',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态:0 禁用 1 启用',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '更新者',
  `create_by` varchar(30) DEFAULT NULL COMMENT '审批流发起人',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='表单字段定义';

-- ----------------------------
-- Table structure for t_flw_process_condition
-- ----------------------------
DROP TABLE IF EXISTS `t_flw_process_condition`;
CREATE TABLE `t_flw_process_condition` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '条件名称',
  `expression` varchar(50) DEFAULT NULL COMMENT '条件表达式',
  `process_type` varchar(5) NOT NULL COMMENT '流程类型,pm,oa',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '更新者',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建者',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='流程条件表达式';

-- ----------------------------
-- Table structure for t_flw_process_definition
-- ----------------------------
DROP TABLE IF EXISTS `t_flw_process_definition`;
CREATE TABLE `t_flw_process_definition` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `process_no` varchar(50) NOT NULL COMMENT '流程定义编号',
  `process_name` varchar(50) NOT NULL COMMENT '流程定义名称',
  `process_type` varchar(5) NOT NULL COMMENT '流程类型,pm,oa',
  `process_desc` varchar(200) DEFAULT NULL COMMENT '流程定义描述',
  `deploy_id` varchar(64) DEFAULT NULL COMMENT '流程部署id',
  `process_version` int(11) NOT NULL DEFAULT '0' COMMENT '审批流版本号',
  `process_file` longtext COMMENT '审批流文件',
  `form_no` varchar(20) DEFAULT NULL COMMENT '表单编号',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  `dept_id` bigint(20) DEFAULT '-1' COMMENT '归属部门',
  `deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除, 1删除/0否',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '流程状态，0草稿，1激活，2挂起或禁用',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '更新者',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建者',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_process_no_version` (`process_no`,`process_version`) USING BTREE,
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='流程定义表';

-- ----------------------------
-- Table structure for t_flw_process_instance
-- ----------------------------
DROP TABLE IF EXISTS `t_flw_process_instance`;
CREATE TABLE `t_flw_process_instance` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `biz_no` varchar(50) NOT NULL COMMENT '业务编号',
  `form_inst_no` varchar(50) DEFAULT NULL COMMENT '表单实例编号',
  `process_type` varchar(5) NOT NULL COMMENT '流程类型',
  `instance_no` varchar(50) NOT NULL COMMENT '实例编号',
  `process_no` varchar(50) NOT NULL COMMENT '流程定义编号',
  `process_version` int(11) NOT NULL DEFAULT '0' COMMENT '流程定义版本',
  `instance_name` varchar(50) NOT NULL COMMENT '实例名称',
  `instance_id` varchar(50) DEFAULT NULL COMMENT '审批流实例ID',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '流程结束时间',
  `dept_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '归属部门',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '需求状态,枚举：-1异常，0初始化，1运行中，2挂起，3完成，4取消',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '更新者',
  `create_by` varchar(30) DEFAULT NULL COMMENT '审批流发起人',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_approval_no` (`instance_no`),
  KEY `idx_process_no` (`process_no`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_process_biz` (`biz_no`,`process_type`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='流程实例表';

-- ----------------------------
-- Table structure for t_flw_process_approval_record
-- ----------------------------
DROP TABLE IF EXISTS `t_flw_process_approval_record`;
CREATE TABLE `t_flw_process_approval_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `instance_no` varchar(50) DEFAULT NULL COMMENT '实例编号',
  `instance_name` varchar(100) DEFAULT NULL COMMENT '实例名称',
  `instance_id` varchar(50) NOT NULL COMMENT '实例ID',
  `task_id` varchar(50) NOT NULL COMMENT '当前节点ID',
  `task_key` varchar(50) NOT NULL COMMENT '节点标识',
  `task_name` varchar(50) DEFAULT NULL COMMENT '当前审批流节点名称',
  `en_assignee` varchar(20) DEFAULT NULL COMMENT '实际审批人登录名',
  `cn_assignee` varchar(20) DEFAULT NULL COMMENT '实际审批人中文名',
  `opt_type` varchar(2) DEFAULT NULL COMMENT '操作类型，1通过，2，退回，3，驳回，5，委派，5，转办，6终止，7撤回',
  `comment` varchar(200) DEFAULT NULL COMMENT '审批意见',
  `candidate` varchar(200) DEFAULT NULL COMMENT '候选人',
  `form_inst_no` varchar(50) DEFAULT NULL COMMENT '表单实例编号',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '节点启动时间',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '当前节点审批时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '更新者',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建者',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `idx_approval_no` (`instance_no`),
  KEY `idx_process_instance_id` (`instance_id`),
  KEY `idx_task_id` (`task_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='流程审批记录表';

```
#### flowable 内置表
```sql
-- ----------------------------
-- Table structure for act_id_property
-- ----------------------------
DROP TABLE IF EXISTS `act_id_property`;
CREATE TABLE `act_id_property` (
  `NAME_` varchar(64) COLLATE utf8_bin NOT NULL,
  `VALUE_` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_app_databasechangeloglock
-- ----------------------------
DROP TABLE IF EXISTS `act_app_databasechangeloglock`;
CREATE TABLE `act_app_databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- ----------------------------
-- Table structure for act_ge_property
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_property`;
CREATE TABLE `act_ge_property` (
  `NAME_` varchar(64) COLLATE utf8_bin NOT NULL,
  `VALUE_` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_app_databasechangelog
-- ----------------------------
DROP TABLE IF EXISTS `act_app_databasechangelog`;
CREATE TABLE `act_app_databasechangelog` (
  `ID` varchar(255) COLLATE utf8_bin NOT NULL,
  `AUTHOR` varchar(255) COLLATE utf8_bin NOT NULL,
  `FILENAME` varchar(255) COLLATE utf8_bin NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) COLLATE utf8_bin NOT NULL,
  `MD5SUM` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `COMMENTS` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TAG` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LIQUIBASE` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `CONTEXTS` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LABELS` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ----------------------------
-- Table structure for act_re_deployment
-- ----------------------------
DROP TABLE IF EXISTS `act_re_deployment`;
CREATE TABLE `act_re_deployment` (
 `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
 `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
 `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
 `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
 `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
 `DEPLOY_TIME_` timestamp(3) NULL DEFAULT NULL,
 `DERIVED_FROM_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
 `DERIVED_FROM_ROOT_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
 `PARENT_DEPLOYMENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
 `ENGINE_VERSION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
 PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ----------------------------
-- Table structure for act_ge_bytearray
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_bytearray`;
CREATE TABLE `act_ge_bytearray` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTES_` longblob,
  `GENERATED_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_BYTEARR_DEPL` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ----------------------------
-- Table structure for act_re_procdef
-- ----------------------------
DROP TABLE IF EXISTS `act_re_procdef`;
CREATE TABLE `act_re_procdef` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint(4) DEFAULT NULL,
  `HAS_GRAPHICAL_NOTATION_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `ENGINE_VERSION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DERIVED_FROM_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DERIVED_FROM_ROOT_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DERIVED_VERSION_` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_PROCDEF` (`KEY_`,`VERSION_`,`DERIVED_VERSION_`,`TENANT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ----------------------------
-- Table structure for act_ru_execution
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_execution`;
CREATE TABLE `act_ru_execution` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_EXEC_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE_` tinyint(4) DEFAULT NULL,
  `IS_CONCURRENT_` tinyint(4) DEFAULT NULL,
  `IS_SCOPE_` tinyint(4) DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint(4) DEFAULT NULL,
  `IS_MI_ROOT_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `CACHED_ENT_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) DEFAULT NULL,
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_COUNT_ENABLED_` tinyint(4) DEFAULT NULL,
  `EVT_SUBSCR_COUNT_` int(11) DEFAULT NULL,
  `TASK_COUNT_` int(11) DEFAULT NULL,
  `JOB_COUNT_` int(11) DEFAULT NULL,
  `TIMER_JOB_COUNT_` int(11) DEFAULT NULL,
  `SUSP_JOB_COUNT_` int(11) DEFAULT NULL,
  `DEADLETTER_JOB_COUNT_` int(11) DEFAULT NULL,
  `EXTERNAL_WORKER_JOB_COUNT_` int(11) DEFAULT NULL,
  `VAR_COUNT_` int(11) DEFAULT NULL,
  `ID_LINK_COUNT_` int(11) DEFAULT NULL,
  `CALLBACK_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CALLBACK_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REFERENCE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REFERENCE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROPAGATED_STAGE_INST_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_STATUS_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`),
  KEY `ACT_IDC_EXEC_ROOT` (`ROOT_PROC_INST_ID_`),
  KEY `ACT_IDX_EXEC_REF_ID_` (`REFERENCE_ID_`),
  KEY `ACT_FK_EXE_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_EXE_PARENT` (`PARENT_ID_`),
  KEY `ACT_FK_EXE_SUPER` (`SUPER_EXEC_`),
  KEY `ACT_FK_EXE_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE,
  CONSTRAINT `ACT_FK_EXE_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_ru_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_job`;
CREATE TABLE `act_ru_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ELEMENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CORRELATION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_JOB_EXCEPTION_STACK_ID` (`EXCEPTION_STACK_ID_`),
  KEY `ACT_IDX_JOB_CUSTOM_VALUES_ID` (`CUSTOM_VALUES_ID_`),
  KEY `ACT_IDX_JOB_CORRELATION_ID` (`CORRELATION_ID_`),
  KEY `ACT_IDX_JOB_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_JOB_SUB_SCOPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_JOB_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_FK_JOB_EXECUTION` (`EXECUTION_ID_`),
  KEY `ACT_FK_JOB_PROCESS_INSTANCE` (`PROCESS_INSTANCE_ID_`),
  KEY `ACT_FK_JOB_PROC_DEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_JOB_CUSTOM_VALUES` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;





-- ----------------------------
-- Table structure for act_hi_procinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_procinst`;
CREATE TABLE `act_hi_procinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT '1',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `END_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CALLBACK_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CALLBACK_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REFERENCE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REFERENCE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROPAGATED_STAGE_INST_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_STATUS_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `PROC_INST_ID_` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PRO_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_PRO_I_BUSKEY` (`BUSINESS_KEY_`),
  KEY `ACT_IDX_HI_PRO_SUPER_PROCINST` (`SUPER_PROCESS_INSTANCE_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_hi_comment
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_comment`;
CREATE TABLE `act_hi_comment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MESSAGE_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `FULL_MSG_` longblob,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- ----------------------------
-- Table structure for act_hi_actinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_actinst`;
CREATE TABLE `act_hi_actinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT '1',
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ACT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `TRANSACTION_ORDER_` int(11) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_ACT_INST_START` (`START_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_PROCINST` (`PROC_INST_ID_`,`ACT_ID_`),
  KEY `ACT_IDX_HI_ACT_INST_EXEC` (`EXECUTION_ID_`,`ACT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_hi_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_identitylink`;
CREATE TABLE `act_hi_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime(3) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_HI_IDENT_LNK_SUB_SCOPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_HI_IDENT_LNK_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_HI_IDENT_LNK_TASK` (`TASK_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_hi_taskinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_taskinst`;
CREATE TABLE `act_hi_taskinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT '1',
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROPAGATED_STAGE_INST_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `CLAIM_TIME_` datetime(3) DEFAULT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `DUE_DATE_` datetime(3) DEFAULT NULL,
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `LAST_UPDATED_TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_TASK_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_HI_TASK_SUB_SCOPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_HI_TASK_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_HI_TASK_INST_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_hi_varinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_varinst`;
CREATE TABLE `act_hi_varinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT '1',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime(3) DEFAULT NULL,
  `LAST_UPDATED_TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_PROCVAR_NAME_TYPE` (`NAME_`,`VAR_TYPE_`),
  KEY `ACT_IDX_HI_VAR_SCOPE_ID_TYPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_HI_VAR_SUB_ID_TYPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_HI_PROCVAR_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PROCVAR_TASK_ID` (`TASK_ID_`),
  KEY `ACT_IDX_HI_PROCVAR_EXE` (`EXECUTION_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_ru_actinst
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_actinst`;
CREATE TABLE `act_ru_actinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT '1',
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ACT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `TRANSACTION_ORDER_` int(11) DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_RU_ACTI_START` (`START_TIME_`),
  KEY `ACT_IDX_RU_ACTI_END` (`END_TIME_`),
  KEY `ACT_IDX_RU_ACTI_PROC` (`PROC_INST_ID_`),
  KEY `ACT_IDX_RU_ACTI_PROC_ACT` (`PROC_INST_ID_`,`ACT_ID_`),
  KEY `ACT_IDX_RU_ACTI_EXEC` (`EXECUTION_ID_`),
  KEY `ACT_IDX_RU_ACTI_EXEC_ACT` (`EXECUTION_ID_`,`ACT_ID_`),
  KEY `ACT_IDX_RU_ACTI_TASK` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ----------------------------
-- Table structure for act_ru_task
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_task`;
CREATE TABLE `act_ru_task` (
`ID_` varchar(64) COLLATE utf8_bin NOT NULL,
`REV_` int(11) DEFAULT NULL,
`EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
`PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
`PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
`TASK_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
`SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`PROPAGATED_STAGE_INST_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
`DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
`TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`DELEGATION_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
`PRIORITY_` int(11) DEFAULT NULL,
`CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
`DUE_DATE_` datetime(3) DEFAULT NULL,
`CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`SUSPENSION_STATE_` int(11) DEFAULT NULL,
`TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
`FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`CLAIM_TIME_` datetime(3) DEFAULT NULL,
`IS_COUNT_ENABLED_` tinyint(4) DEFAULT NULL,
`VAR_COUNT_` int(11) DEFAULT NULL,
`ID_LINK_COUNT_` int(11) DEFAULT NULL,
`SUB_TASK_COUNT_` int(11) DEFAULT NULL,
PRIMARY KEY (`ID_`),
KEY `ACT_IDX_TASK_CREATE` (`CREATE_TIME_`),
KEY `ACT_IDX_TASK_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
KEY `ACT_IDX_TASK_SUB_SCOPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
KEY `ACT_IDX_TASK_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`),
KEY `ACT_FK_TASK_EXE` (`EXECUTION_ID_`),
KEY `ACT_FK_TASK_PROCINST` (`PROC_INST_ID_`),
KEY `ACT_FK_TASK_PROCDEF` (`PROC_DEF_ID_`),
CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;




-- ----------------------------
-- Table structure for act_ru_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_identitylink`;
CREATE TABLE `act_ru_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_IDENT_LNK_GROUP` (`GROUP_ID_`),
  KEY `ACT_IDX_IDENT_LNK_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_IDENT_LNK_SUB_SCOPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_IDENT_LNK_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_ATHRZ_PROCEDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_TSKASS_TASK` (`TASK_ID_`),
  KEY `ACT_FK_IDL_PROCINST` (`PROC_INST_ID_`),
  CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_IDL_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `act_ru_task` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- ----------------------------
-- Table structure for act_ru_variable
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_variable`;
CREATE TABLE `act_ru_variable` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_RU_VAR_SCOPE_ID_TYPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_RU_VAR_SUB_ID_TYPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_FK_VAR_BYTEARRAY` (`BYTEARRAY_ID_`),
  KEY `ACT_IDX_VARIABLE_TASK_ID` (`TASK_ID_`),
  KEY `ACT_FK_VAR_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_VAR_PROCINST` (`PROC_INST_ID_`),
  CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for flw_ev_databasechangelog
-- ----------------------------
DROP TABLE IF EXISTS `flw_ev_databasechangelog`;
CREATE TABLE `flw_ev_databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of flw_ev_databasechangelog
-- ----------------------------


-- ----------------------------
-- Table structure for flw_ev_databasechangeloglock
-- ----------------------------
DROP TABLE IF EXISTS `flw_ev_databasechangeloglock`;
CREATE TABLE `flw_ev_databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS `act_ru_external_job`;
CREATE TABLE `act_ru_external_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ELEMENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CORRELATION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EXTERNAL_JOB_EXCEPTION_STACK_ID` (`EXCEPTION_STACK_ID_`),
  KEY `ACT_IDX_EXTERNAL_JOB_CUSTOM_VALUES_ID` (`CUSTOM_VALUES_ID_`),
  KEY `ACT_IDX_EXTERNAL_JOB_CORRELATION_ID` (`CORRELATION_ID_`),
  KEY `ACT_IDX_EJOB_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_EJOB_SUB_SCOPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_EJOB_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`),
  CONSTRAINT `ACT_FK_EXTERNAL_JOB_CUSTOM_VALUES` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_EXTERNAL_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


DROP TABLE IF EXISTS `act_ru_timer_job`;
CREATE TABLE `act_ru_timer_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ELEMENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CORRELATION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_TIMER_JOB_EXCEPTION_STACK_ID` (`EXCEPTION_STACK_ID_`),
  KEY `ACT_IDX_TIMER_JOB_CUSTOM_VALUES_ID` (`CUSTOM_VALUES_ID_`),
  KEY `ACT_IDX_TIMER_JOB_CORRELATION_ID` (`CORRELATION_ID_`),
  KEY `ACT_IDX_TIMER_JOB_DUEDATE` (`DUEDATE_`),
  KEY `ACT_IDX_TJOB_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_TJOB_SUB_SCOPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_TJOB_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_FK_TIMER_JOB_EXECUTION` (`EXECUTION_ID_`),
  KEY `ACT_FK_TIMER_JOB_PROCESS_INSTANCE` (`PROCESS_INSTANCE_ID_`),
  KEY `ACT_FK_TIMER_JOB_PROC_DEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_TIMER_JOB_CUSTOM_VALUES` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_TIMER_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_TIMER_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TIMER_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TIMER_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `flw_channel_definition`;
CREATE TABLE `flw_channel_definition` (
  `ID_` varchar(255) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `VERSION_` int(11) DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `CATEGORY_` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(255) DEFAULT NULL,
  `CREATE_TIME_` datetime(3) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT NULL,
  `RESOURCE_NAME_` varchar(255) DEFAULT NULL,
  `DESCRIPTION_` varchar(255) DEFAULT NULL,
  `TYPE_` varchar(255) DEFAULT NULL,
  `IMPLEMENTATION_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE KEY `ACT_IDX_CHANNEL_DEF_UNIQ` (`KEY_`,`VERSION_`,`TENANT_ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS `act_procdef_info`;
CREATE TABLE `act_procdef_info` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `INFO_JSON_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_IDX_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_INFO_JSON_BA` (`INFO_JSON_ID_`),
  CONSTRAINT `ACT_FK_INFO_JSON_BA` FOREIGN KEY (`INFO_JSON_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_INFO_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- 缺少此表，部署不了
CREATE TABLE `act_ru_event_subscr` (
`ID_` varchar(64) COLLATE utf8_bin NOT NULL,
`REV_` int(11) DEFAULT NULL,
`EVENT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
`EVENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
`PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
`ACTIVITY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
`CONFIGURATION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`CREATED_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
`PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
`SUB_SCOPE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
`SCOPE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
`SCOPE_DEFINITION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
`SCOPE_TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
`LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
`LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
PRIMARY KEY (`ID_`),
KEY `ACT_IDX_EVENT_SUBSCR_CONFIG_` (`CONFIGURATION_`),
KEY `ACT_IDX_EVENT_SUBSCR_SCOPEREF_` (`SCOPE_ID_`,`SCOPE_TYPE_`),
KEY `ACT_FK_EVENT_EXEC` (`EXECUTION_ID_`),
CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `act_ru_suspended_job` (
`ID_` varchar(64) COLLATE utf8_bin NOT NULL,
`REV_` int(11) DEFAULT NULL,
`CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
`EXCLUSIVE_` tinyint(1) DEFAULT NULL,
`EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
`PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
`PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
`ELEMENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`ELEMENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`CORRELATION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`RETRIES_` int(11) DEFAULT NULL,
`EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
`EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
`DUEDATE_` timestamp(3) NULL DEFAULT NULL,
`REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
`HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
`CUSTOM_VALUES_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
`CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
`TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
PRIMARY KEY (`ID_`),
KEY `ACT_IDX_SUSPENDED_JOB_EXCEPTION_STACK_ID` (`EXCEPTION_STACK_ID_`),
KEY `ACT_IDX_SUSPENDED_JOB_CUSTOM_VALUES_ID` (`CUSTOM_VALUES_ID_`),
KEY `ACT_IDX_SUSPENDED_JOB_CORRELATION_ID` (`CORRELATION_ID_`),
KEY `ACT_IDX_SJOB_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
KEY `ACT_IDX_SJOB_SUB_SCOPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
KEY `ACT_IDX_SJOB_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`),
KEY `ACT_FK_SUSPENDED_JOB_EXECUTION` (`EXECUTION_ID_`),
KEY `ACT_FK_SUSPENDED_JOB_PROCESS_INSTANCE` (`PROCESS_INSTANCE_ID_`),
KEY `ACT_FK_SUSPENDED_JOB_PROC_DEF` (`PROC_DEF_ID_`),
CONSTRAINT `ACT_FK_SUSPENDED_JOB_CUSTOM_VALUES` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
CONSTRAINT `ACT_FK_SUSPENDED_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
CONSTRAINT `ACT_FK_SUSPENDED_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
CONSTRAINT `ACT_FK_SUSPENDED_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`),
CONSTRAINT `ACT_FK_SUSPENDED_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



CREATE TABLE `act_ru_deadletter_job` (
 `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
 `REV_` int(11) DEFAULT NULL,
 `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
 `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
 `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
 `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
 `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
 `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
 `ELEMENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
 `ELEMENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
 `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
 `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
 `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
 `SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
 `CORRELATION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
 `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
 `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
 `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
 `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
 `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
 `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
 `CUSTOM_VALUES_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
 `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
 `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
 PRIMARY KEY (`ID_`),
 KEY `ACT_IDX_DEADLETTER_JOB_EXCEPTION_STACK_ID` (`EXCEPTION_STACK_ID_`),
 KEY `ACT_IDX_DEADLETTER_JOB_CUSTOM_VALUES_ID` (`CUSTOM_VALUES_ID_`),
 KEY `ACT_IDX_DEADLETTER_JOB_CORRELATION_ID` (`CORRELATION_ID_`),
 KEY `ACT_IDX_DJOB_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
 KEY `ACT_IDX_DJOB_SUB_SCOPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
 KEY `ACT_IDX_DJOB_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`),
 KEY `ACT_FK_DEADLETTER_JOB_EXECUTION` (`EXECUTION_ID_`),
 KEY `ACT_FK_DEADLETTER_JOB_PROCESS_INSTANCE` (`PROCESS_INSTANCE_ID_`),
 KEY `ACT_FK_DEADLETTER_JOB_PROC_DEF` (`PROC_DEF_ID_`),
 CONSTRAINT `ACT_FK_DEADLETTER_JOB_CUSTOM_VALUES` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
 CONSTRAINT `ACT_FK_DEADLETTER_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
 CONSTRAINT `ACT_FK_DEADLETTER_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
 CONSTRAINT `ACT_FK_DEADLETTER_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`),
 CONSTRAINT `ACT_FK_DEADLETTER_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;




```

### 初始化数据
#### 自定义初始化
```sql
INSERT INTO `platform`.`t_flw_process_definition` (`id`, `process_no`, `process_name`, `process_type`, `process_desc`, `deploy_id`, `process_version`, `process_file`, `form_no`, `tenant_id`, `dept_id`, `deleted`, `status`, `create_time`, `update_time`, `update_by`, `create_by`, `remark`) VALUES (1, 'P2311300000', '全流程', 'pm', NULL, 'af261426-9f12-11ee-afa4-1a743aeedab3', 1, '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:flowable=\"http://flowable.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:bpmn2=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\" expressionLanguage=\"http://www.w3.org/1999/XPath\" targetNamespace=\"http://flowable.org/bpmn\" id=\"diagram_Process_1699944087252\" xsi:schemaLocation=\"http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd\">\n  <process id=\"P2311300000\" name=\"全流程\" isExecutable=\"true\">\n    <extensionElements>\n      <flowable:properties></flowable:properties>\n    </extensionElements>\n    <startEvent id=\"start\" name=\"需求填报\" flowable:formKey=\"key_FNO23111900001\"></startEvent>\n    <userTask id=\"demandAnalysis\" name=\"需求分析\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"PM\" flowable:formKey=\"key_FNO23111900001\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"产品经理\" flowable:dataType=\"ROLES\">\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_1nzv519\" sourceRef=\"start\" targetRef=\"demandAnalysis\"></sequenceFlow>\n    <userTask id=\"srr\" name=\"需求评审\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"PMO,PM,UI,FED,BED,QA,SA,TD,TL\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"产品经理,项目经理,UI设计,前端开发,后端开发,测试,架构师,技术负责人,团队负责人\">\n      <extensionElements>\n        <flowable:properties>\n          <flowable:property name=\"owner\" value=\"PMO\"></flowable:property>\n        </flowable:properties>\n      </extensionElements>\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_1f9m8ly\" sourceRef=\"demandAnalysis\" targetRef=\"srr\"></sequenceFlow>\n    <userTask id=\"softDesign\" name=\"技术设计\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"FED,BED,SA,TD\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"前端开发,后端开发,架构师,技术负责人\">\n      <extensionElements>\n        <flowable:properties>\n          <flowable:property name=\"owner\" value=\"TD\"></flowable:property>\n        </flowable:properties>\n      </extensionElements>\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <userTask id=\"designReview\" name=\"设计评审\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"PM,PMO,UI,FED,BED,QA,SA,TD,TL\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"产品经理,项目经理,UI设计,前端开发,后端开发,测试,架构师,技术负责人,团队负责人\">\n      <extensionElements>\n        <flowable:executionListener event=\"end\" class=\"com.github.platform.core.pm.infra.listener.PmSplitConditionListener\"></flowable:executionListener>\n        <flowable:properties>\n          <flowable:property name=\"owner\" value=\"TD\"></flowable:property>\n        </flowable:properties>\n      </extensionElements>\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_0ssrn24\" sourceRef=\"softDesign\" targetRef=\"designReview\"></sequenceFlow>\n    <userTask id=\"fedDev\" name=\"前端开发\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"FED\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"前端开发\">\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <userTask id=\"qaCase\" name=\"用例编写\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"QA\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"测试\">\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <userTask id=\"caseReview\" name=\"用例评审\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"QA,FED,BED\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"前端开发,后端开发,测试\">\n      <extensionElements>\n        <flowable:properties>\n          <flowable:property name=\"owner\" value=\"QA\"></flowable:property>\n        </flowable:properties>\n      </extensionElements>\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_02j1ci7\" sourceRef=\"qaCase\" targetRef=\"caseReview\"></sequenceFlow>\n    <userTask id=\"qaTest\" name=\"测试\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"QA,FED,BED\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"前端开发,后端开发,测试\">\n      <extensionElements>\n        <flowable:properties>\n          <flowable:property name=\"owner\" value=\"QA\"></flowable:property>\n        </flowable:properties>\n      </extensionElements>\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <userTask id=\"preCheck\" name=\"预验收\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"UI,PM\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"产品经理,UI设计\">\n      <extensionElements>\n        <flowable:properties>\n          <flowable:property name=\"owner\" value=\"PM\"></flowable:property>\n        </flowable:properties>\n      </extensionElements>\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_0x73fal\" sourceRef=\"qaTest\" targetRef=\"preCheck\"></sequenceFlow>\n    <userTask id=\"online\" name=\"上线\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"FED,BED,TD,QA,PMO\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"项目经理,前端开发,后端开发,测试,技术负责人\">\n      <extensionElements>\n        <flowable:properties>\n          <flowable:property name=\"owner\" value=\"TD\"></flowable:property>\n        </flowable:properties>\n      </extensionElements>\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_07h9c2e\" sourceRef=\"preCheck\" targetRef=\"online\"></sequenceFlow>\n    <endEvent id=\"end\" name=\"完结\"></endEvent>\n    <sequenceFlow id=\"Flow_0a8rw0s\" sourceRef=\"pmCheck\" targetRef=\"end\"></sequenceFlow>\n    <userTask id=\"bedDev\" name=\"后端开发\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"BED\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"后端开发\">\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_06nkr92\" sourceRef=\"designReview\" targetRef=\"bedDev\">\n      <conditionExpression xsi:type=\"tFormalExpression\">${hasBED}</conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow id=\"Flow_0el54ur\" sourceRef=\"designReview\" targetRef=\"fedDev\">\n      <conditionExpression xsi:type=\"tFormalExpression\">${hasFED}</conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow id=\"Flow_1lqq0ny\" sourceRef=\"designReview\" targetRef=\"qaCase\">\n      <conditionExpression xsi:type=\"tFormalExpression\">${hasQA}</conditionExpression>\n    </sequenceFlow>\n    <userTask id=\"pmCheck\" name=\"产品验收\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"PM\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"产品经理\">\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_0lb98w1\" sourceRef=\"online\" targetRef=\"pmCheck\"></sequenceFlow>\n    <userTask id=\"jointDebug\" name=\"联调\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"TD\" flowable:formKey=\"key_FNO23111900001\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:localScope=\"true\" flowable:assigneeType=\"ROLES\" flowable:text=\"技术负责人\">\n      <extensionElements>\n        <flowable:properties>\n          <flowable:property name=\"owner\" value=\"TD\"></flowable:property>\n        </flowable:properties>\n      </extensionElements>\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_0scubyn\" name=\"提测\" sourceRef=\"jointDebug\" targetRef=\"qaTest\"></sequenceFlow>\n    <sequenceFlow id=\"Flow_1mncygv\" sourceRef=\"bedDev\" targetRef=\"Gateway_068n941\"></sequenceFlow>\n    <sequenceFlow id=\"Flow_11wzaac\" sourceRef=\"fedDev\" targetRef=\"Gateway_068n941\"></sequenceFlow>\n    <sequenceFlow id=\"Flow_1u66wiv\" sourceRef=\"caseReview\" targetRef=\"Gateway_068n941\"></sequenceFlow>\n    <sequenceFlow id=\"Flow_1mn9llr\" sourceRef=\"Gateway_068n941\" targetRef=\"jointDebug\"></sequenceFlow>\n    <parallelGateway id=\"Gateway_068n941\"></parallelGateway>\n    <userTask id=\"planSchedule\" name=\"预排期\" flowable:assignee=\"${assignee}\" flowable:candidateGroups=\"PMO,UI,FED,BED,QA,TD\" xmlns:flowable=\"http://flowable.org/bpmn\" flowable:assigneeType=\"ROLES\" flowable:text=\"项目经理,UI设计,前端开发,后端开发,测试,技术负责人\">\n      <extensionElements>\n        <flowable:properties>\n          <flowable:property name=\"owner\" value=\"PMO\"></flowable:property>\n        </flowable:properties>\n      </extensionElements>\n      <multiInstanceLoopCharacteristics isSequential=\"false\" flowable:collection=\"${multiInstanceHandler.getUsers(execution)}\" flowable:elementVariable=\"assignee\">\n        <completionCondition>${nrOfCompletedInstances &gt; 0}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <sequenceFlow id=\"Flow_1g7ggjh\" sourceRef=\"srr\" targetRef=\"planSchedule\"></sequenceFlow>\n    <sequenceFlow id=\"Flow_1ftzke4\" sourceRef=\"planSchedule\" targetRef=\"softDesign\"></sequenceFlow>\n  </process>\n  <bpmndi:BPMNDiagram id=\"BPMNDiagram_P2311300000\">\n    <bpmndi:BPMNPlane bpmnElement=\"P2311300000\" id=\"BPMNPlane_P2311300000\">\n      <bpmndi:BPMNShape bpmnElement=\"start\" id=\"BPMNShape_start\">\n        <omgdc:Bounds height=\"36.0\" width=\"36.0\" x=\"392.0\" y=\"272.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"demandAnalysis\" id=\"BPMNShape_demandAnalysis\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"470.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"srr\" id=\"BPMNShape_srr\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"610.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"softDesign\" id=\"BPMNShape_softDesign\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"890.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"designReview\" id=\"BPMNShape_designReview\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"1020.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"fedDev\" id=\"BPMNShape_fedDev\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"1170.0\" y=\"140.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"qaCase\" id=\"BPMNShape_qaCase\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"1170.0\" y=\"360.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"caseReview\" id=\"BPMNShape_caseReview\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"1350.0\" y=\"360.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"qaTest\" id=\"BPMNShape_qaTest\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"1630.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"preCheck\" id=\"BPMNShape_preCheck\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"1790.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"online\" id=\"BPMNShape_online\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"1930.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"end\" id=\"BPMNShape_end\">\n        <omgdc:Bounds height=\"36.0\" width=\"36.0\" x=\"2212.0\" y=\"272.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"bedDev\" id=\"BPMNShape_bedDev\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"1170.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"pmCheck\" id=\"BPMNShape_pmCheck\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"2070.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"jointDebug\" id=\"BPMNShape_jointDebug\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"1470.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"Gateway_068n941\" id=\"BPMNShape_Gateway_068n941\">\n        <omgdc:Bounds height=\"50.0\" width=\"50.0\" x=\"1375.0\" y=\"265.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"planSchedule\" id=\"BPMNShape_planSchedule\">\n        <omgdc:Bounds height=\"80.0\" width=\"100.0\" x=\"750.0\" y=\"250.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_1nzv519\" id=\"BPMNEdge_Flow_1nzv519\">\n        <omgdi:waypoint x=\"428.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"470.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_1f9m8ly\" id=\"BPMNEdge_Flow_1f9m8ly\">\n        <omgdi:waypoint x=\"570.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"610.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_0ssrn24\" id=\"BPMNEdge_Flow_0ssrn24\">\n        <omgdi:waypoint x=\"990.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1020.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_02j1ci7\" id=\"BPMNEdge_Flow_02j1ci7\">\n        <omgdi:waypoint x=\"1270.0\" y=\"400.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1350.0\" y=\"400.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_0x73fal\" id=\"BPMNEdge_Flow_0x73fal\">\n        <omgdi:waypoint x=\"1730.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1790.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_07h9c2e\" id=\"BPMNEdge_Flow_07h9c2e\">\n        <omgdi:waypoint x=\"1890.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1930.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_0a8rw0s\" id=\"BPMNEdge_Flow_0a8rw0s\">\n        <omgdi:waypoint x=\"2170.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"2212.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_06nkr92\" id=\"BPMNEdge_Flow_06nkr92\">\n        <omgdi:waypoint x=\"1120.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1170.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_0el54ur\" id=\"BPMNEdge_Flow_0el54ur\">\n        <omgdi:waypoint x=\"1070.0\" y=\"250.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1070.0\" y=\"180.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1170.0\" y=\"180.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_1lqq0ny\" id=\"BPMNEdge_Flow_1lqq0ny\">\n        <omgdi:waypoint x=\"1070.0\" y=\"330.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1070.0\" y=\"400.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1170.0\" y=\"400.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_0lb98w1\" id=\"BPMNEdge_Flow_0lb98w1\">\n        <omgdi:waypoint x=\"2030.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"2070.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_0scubyn\" id=\"BPMNEdge_Flow_0scubyn\">\n        <omgdi:waypoint x=\"1570.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1630.0\" y=\"290.0\"></omgdi:waypoint>\n        <bpmndi:BPMNLabel>\n          <omgdc:Bounds height=\"14.0\" width=\"22.0\" x=\"1589.0\" y=\"272.0\"></omgdc:Bounds>\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_1mncygv\" id=\"BPMNEdge_Flow_1mncygv\">\n        <omgdi:waypoint x=\"1270.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1375.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_11wzaac\" id=\"BPMNEdge_Flow_11wzaac\">\n        <omgdi:waypoint x=\"1270.0\" y=\"180.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1400.0\" y=\"180.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1400.0\" y=\"265.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_1u66wiv\" id=\"BPMNEdge_Flow_1u66wiv\">\n        <omgdi:waypoint x=\"1400.0\" y=\"360.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1400.0\" y=\"315.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_1mn9llr\" id=\"BPMNEdge_Flow_1mn9llr\">\n        <omgdi:waypoint x=\"1425.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"1470.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_1g7ggjh\" id=\"BPMNEdge_Flow_1g7ggjh\">\n        <omgdi:waypoint x=\"710.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"750.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"Flow_1ftzke4\" id=\"BPMNEdge_Flow_1ftzke4\">\n        <omgdi:waypoint x=\"850.0\" y=\"290.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"890.0\" y=\"290.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n    </bpmndi:BPMNPlane>\n  </bpmndi:BPMNDiagram>\n</definitions>', NULL, 1001, 1, 0, 0, '2023-11-30 16:35:32', '2024-01-03 22:21:29', 'yxkong', 'admin', NULL);
```

#### flowable 初始化
```sql
INSERT INTO `act_id_property` VALUES ('schema.version', '6.8.0.0', 1);
INSERT INTO `act_app_databasechangeloglock` VALUES (1, b'0', NULL, NULL);

INSERT INTO `act_ge_property` VALUES ('batch.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('cfg.execution-related-entities-count', 'true', 1);
INSERT INTO `act_ge_property` VALUES ('cfg.task-related-entities-count', 'true', 1);
INSERT INTO `act_ge_property` VALUES ('common.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('entitylink.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('eventsubscription.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('identitylink.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('job.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('next.dbid', '1', 1);
INSERT INTO `act_ge_property` VALUES ('schema.history', 'create(6.8.0.0)', 1);
INSERT INTO `act_ge_property` VALUES ('schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('task.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('variable.schema.version', '6.8.0.0', 1);
```