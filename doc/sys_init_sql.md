```sql

-- ----------------------------
-- Table structure for t_code_column_config
-- ----------------------------
DROP TABLE IF EXISTS `t_code_column_config`;
CREATE TABLE `t_code_column_config` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `table_name` varchar(50) DEFAULT NULL COMMENT '表名',
    `column_name` varchar(30) DEFAULT NULL COMMENT '数据库字段名称',
    `column_type` varchar(30) DEFAULT NULL COMMENT '数据库字段类型',
    `dict_name` varchar(30) DEFAULT NULL COMMENT '字典名称',
    `extra` varchar(50) DEFAULT NULL COMMENT '字段额外的参数',
    `query_show` tinyint(4) DEFAULT NULL COMMENT '是否作为查询条件，0否，1是，默认null',
    `form_show` tinyint(4) DEFAULT NULL COMMENT '是否表单显示',
    `form_type` varchar(20) DEFAULT NULL COMMENT '表单类型',
    `column_key` varchar(20) DEFAULT NULL COMMENT '数据库字段键类型',
    `list_show` tinyint(4) DEFAULT NULL COMMENT '是否在列表显示，1显示',
    `not_null` tinyint(4) DEFAULT NULL COMMENT '是否必填1，必填',
    `query_type` varchar(20) DEFAULT NULL COMMENT '查询方式，=,>=,>,<,<=,!=,like,isNull,notNull,between,!=',
    `tenant` varchar(10) DEFAULT NULL COMMENT '租户',
    `status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0停用）',
    `sort` tinyint(4) DEFAULT '1' COMMENT '字段排序',
    `create_by` varchar(20) DEFAULT '' COMMENT '创建人(数据归属人)，存储登录名',
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` varchar(20) DEFAULT '' COMMENT '更新人，存储登录名',
    `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` varchar(200) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `unq_table_column` (`table_name`,`column_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='代码生成字段信息存储';

-- ----------------------------
-- Table structure for t_code_gen_config
-- ----------------------------
DROP TABLE IF EXISTS `t_code_gen_config`;
CREATE TABLE `t_code_gen_config` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
 `table_name` varchar(50) DEFAULT NULL COMMENT '表名',
 `table_comment` varchar(200) DEFAULT NULL COMMENT '表描述',
 `author` varchar(30) DEFAULT NULL COMMENT '作者',
 `cover` tinyint(4) DEFAULT NULL COMMENT '是否覆盖',
 `url_prefix` varchar(50) DEFAULT NULL COMMENT 'url前缀',
 `entity_name` varchar(30) DEFAULT NULL COMMENT '实体名称',
 `module_name` varchar(20) DEFAULT NULL COMMENT '模块名称',
 `package_name` varchar(100) DEFAULT NULL COMMENT '生成包路径',
 `path` varchar(100) DEFAULT NULL COMMENT '前端代码生成的路径',
 `api_path` varchar(100) DEFAULT NULL COMMENT '前端Api文件路径',
 `prefix` varchar(20) DEFAULT NULL COMMENT '表前缀',
 `api_alias` varchar(30) DEFAULT NULL COMMENT '接口名称',
 `tenant` varchar(10) DEFAULT NULL COMMENT '租户',
 `status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0停用）',
 `create_by` varchar(20) DEFAULT '' COMMENT '创建人(数据归属人)，存储登录名',
 `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_by` varchar(20) DEFAULT '' COMMENT '更新人，存储登录名',
 `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 `remark` varchar(500) DEFAULT NULL COMMENT '备注',
 PRIMARY KEY (`id`) USING BTREE,
 KEY `idx_table_name` (`table_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='代码生成器配置';

-- ----------------------------
-- Table structure for t_sys_cache_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_cache_log`;
CREATE TABLE `t_sys_cache_log` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`cache_key` varchar(200) NOT NULL COMMENT '缓存key',
`type` varchar(20) DEFAULT NULL COMMENT '缓存类型',
`method` varchar(200) DEFAULT NULL COMMENT '方法',
`command` varchar(30) DEFAULT NULL COMMENT 'redis命令',
`expire_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '过期时间',
`tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
`create_by` varchar(20) DEFAULT '' COMMENT '创建人(数据归属人)，存储登录名',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_by` varchar(20) DEFAULT '' COMMENT '更新人，存储登录名',
`update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
`remark` varchar(200) DEFAULT '' COMMENT '备注',
PRIMARY KEY (`id`),
UNIQUE KEY `IDX_CACHE_KEY` (`cache_key`),
KEY `IDX_EXPIRE_TIME` (`expire_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='缓存表';
-- ----------------------------
-- Table structure for t_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_config`;
CREATE TABLE `t_sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(50) DEFAULT '' COMMENT '参数名称',
  `key` varchar(50) DEFAULT '' COMMENT '参数键名',
  `value` varchar(500) DEFAULT '' COMMENT '参数键值',
  `module` varchar(20) DEFAULT NULL COMMENT '业务模块',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `create_by` varchar(20) DEFAULT '' COMMENT '创建人(数据归属人)，存储登录名',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(20) DEFAULT '' COMMENT '更新人，存储登录名',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='参数配置表';

-- ----------------------------
-- Records of t_sys_config
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_config` VALUES (1, '默认密码', 'sys_default_password', '000000', 'user', 1001, 1, 'yxkong', '2023-04-27 14:33:42', 'renyangqi', '2023-12-22 09:55:50', '添加用户默认密码');
COMMIT;

-- ----------------------------
-- Table structure for t_sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dept`;
CREATE TABLE `t_sys_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `sort` int(11) DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `leader_mobile` varchar(11) DEFAULT NULL COMMENT '负责人电话号',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `create_by` varchar(20) DEFAULT '' COMMENT '创建人(数据归属人)，存储登录名',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(20) DEFAULT '' COMMENT '更新人，存储登录名',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='部门表';

-- ----------------------------
-- Records of t_sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_dept` VALUES (-1, -1, '', 'unknown', 0, NULL, NULL, NULL, 0, '', '2023-05-31 18:01:42', '', '2023-12-18 16:59:15', NULL);
INSERT INTO `t_sys_dept` VALUES (1, 0, '0', '后台科技', 0, 'admin', '13888888888', 1001, 1, '', '2023-04-04 16:08:49', 'yxkong', '2023-04-11 14:14:17', NULL);
INSERT INTO `t_sys_dept` VALUES (2, 1, '0,1', '科技部', 0, 'xxx', NULL, 1001, 1, 'yxkong', '2023-04-08 09:55:27', 'yxkong', '2023-04-08 20:57:44', NULL);
INSERT INTO `t_sys_dept` VALUES (3, 1, '0,1', '产品部', 0, 'xxx', NULL, 1001, 1, 'yxkong', '2023-04-08 20:58:35', '', '2023-04-08 20:58:35', NULL);
INSERT INTO `t_sys_dept` VALUES (4, 2, '0,1,2', '开发组', 0, '11', NULL, 1001, 1, 'yxkong', '2023-04-08 20:58:48', 'yxkong', '2023-04-08 20:59:05', NULL);
INSERT INTO `t_sys_dept` VALUES (5, 2, '0,1,2', '测试组', 0, NULL, NULL, 1001, 1, 'yxkong', '2023-04-08 20:58:58', '', '2023-04-08 20:58:58', NULL);
INSERT INTO `t_sys_dept` VALUES (6, 2, '0,1,2', '项目组', 0, NULL, NULL, 1001, 1, 'yxkong', '2023-04-08 20:59:12', 'admin', '2023-09-13 16:04:51', NULL);
INSERT INTO `t_sys_dept` VALUES (7, 6, '0,1,2,6', '项目1组', 0, '大幅度', '18612340987', 1001, 1, 'admin', '2023-04-28 14:23:18', '', '2023-04-28 14:23:18', NULL);
INSERT INTO `t_sys_dept` VALUES (8, 6, '0,1,2,6', '项目2组', 0, NULL, NULL, 1001, 1, 'admin', '2023-04-28 14:25:00', '', '2023-04-28 14:25:00', NULL);
INSERT INTO `t_sys_dept` VALUES (10, 3, '0,1,3', '2', 1, NULL, NULL, 1001, 1, 'admin', '2023-05-08 18:39:12', '', '2023-05-08 18:39:12', NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict_type`;
CREATE TABLE `t_sys_dict_type` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`name` varchar(100) DEFAULT '' COMMENT '字典名称',
`classify` tinyint(4) DEFAULT '0' COMMENT '字典分类，0系统，1业务',
`type` varchar(100) DEFAULT '' COMMENT '字典类型',
`char_type` varchar(10) DEFAULT NULL COMMENT '字符类型',
`tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
`status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0停用）',
`create_by` varchar(20) DEFAULT '' COMMENT '创建人(数据归属人)，存储登录名',
`create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_by` varchar(20) DEFAULT '' COMMENT '更新人，存储登录名',
`update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
`remark` varchar(200) DEFAULT NULL COMMENT '备注',
PRIMARY KEY (`id`) USING BTREE,
KEY `idx_dict_type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='字典类型表';

-- ----------------------------
-- Records of t_sys_dict_type
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_dict_type` VALUES (1, '性别', 0, 'sys_user_sex', 'int', 1001, 1, 'init', '2023-02-13 14:03:28', 'admin', '2023-04-12 16:28:38', NULL);
INSERT INTO `t_sys_dict_type` VALUES (2, '字符类型', 0, 'sys_char_type', 'str', 1001, 1, 'init', '2023-04-12 16:27:20', '', '2023-04-12 16:28:31', NULL);
INSERT INTO `t_sys_dict_type` VALUES (3, '常用状态', 0, 'sys_status', 'int', 1001, 1, 'init', '2023-02-13 18:03:01', 'admin', '2023-04-12 16:28:34', NULL);
INSERT INTO `t_sys_dict_type` VALUES (4, '数据权限范围', 0, 'sys_datascope', 'int', 1001, 1, 'init', '2023-02-14 15:50:07', 'admin', '2023-04-12 16:28:36', NULL);
INSERT INTO `t_sys_dict_type` VALUES (5, '列表样式', 0, 'sys_list_class', 'str', 1001, 1, 'yxkong', '2023-04-19 18:01:35', '', '2023-04-19 18:01:35', '字典在列表显示的样式');
INSERT INTO `t_sys_dict_type` VALUES (6, '代码生成字典配置', 0, 'sys_gen_code_dict', 'str', 1001, 1, 'admin', '2023-04-28 18:00:49', '', '2023-04-28 18:00:49', NULL);
INSERT INTO `t_sys_dict_type` VALUES (7, '表单类型', 0, 'sys_form_type', 'str', 1001, 1, 'yxkong', '2023-05-05 17:13:38', '', '2023-05-05 17:13:38', NULL);
INSERT INTO `t_sys_dict_type` VALUES (8, '查询方式', 0, 'sys_query_type', 'str', 1001, 1, 'yxkong', '2023-05-05 17:56:06', 'admin', '2023-08-16 13:42:37', NULL);
INSERT INTO `t_sys_dict_type` VALUES (9, '系统模块', 0, 'sys_module', 'str', 1001, 1, 'yxkong', '2023-05-15 18:18:22', 'admin', '2023-08-16 14:24:55', NULL);
INSERT INTO `t_sys_dict_type` VALUES (10, '用户类型', 0, 'sys_user_type', 'int', 1001, 1, 'yxkong', '2023-05-31 15:48:48', '', '2023-05-31 15:48:48', NULL);
INSERT INTO `t_sys_dict_type` VALUES (11, '用户来源', 0, 'sys_user_channel', 'str', 1001, 1, 'yxkong', '2023-05-31 15:49:10', 'admin', '2023-09-13 16:06:50', NULL);
INSERT INTO `t_sys_dict_type` VALUES (12, '分类', 0, 'sys_classify', 'int', 1001, 1, 'yxkong', '2023-07-05 16:42:37', '', '2023-07-05 16:42:37', '用于区分系统和业务');
INSERT INTO `t_sys_dict_type` VALUES (13, '短信节点', 0, 'sys_sms_node', 'str', 1001, 1, 'yxkong', '2023-07-11 18:52:48', '', '2023-07-11 18:52:48', '短信发送节点');
INSERT INTO `t_sys_dict_type` VALUES (14, '定制化业务节点', 1, 'transform_node', 'str', NULL, 1, 'admin', '2023-08-07 17:22:17', '', '2023-08-07 17:22:17', NULL);
INSERT INTO `t_sys_dict_type` VALUES (15, '系统租户', 0, 'sys_tenant', 'int', 1001, 1, 'admin', '2023-10-10 09:59:49', '', '2023-10-10 09:59:49', NULL);
INSERT INTO `t_sys_dict_type` VALUES (16, '流程类型', 1, 'flow_process_type', 'str', 1001, 1, 'admin', '2023-10-10 10:40:04', 'yxkong', '2023-11-21 17:58:10', NULL);
INSERT INTO `t_sys_dict_type` VALUES (17, '任务类型', 1, 'pm_task_type', 'str', 1001, 1, 'yxkong', '2023-12-08 12:27:42', 'yxkong', '2023-12-25 16:04:41', '项目排期的节点,字段长度控制在20');
INSERT INTO `t_sys_dict_type` VALUES (18, '排期节点', 1, 'pm_schedule_node', 'str', 1001, 1, 'yxkong', '2023-12-20 16:04:41', 'yxkong', '2023-12-25 16:04:28', '项目排期的节点,字段长度控制在20');
COMMIT;
-- ----------------------------
-- Table structure for t_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict`;
CREATE TABLE `t_sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `key` varchar(100) NOT NULL DEFAULT '' COMMENT '字典键值',
  `label` varchar(100) NOT NULL DEFAULT '' COMMENT '字典标签',
  `dict_type` varchar(100) NOT NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(50) DEFAULT NULL COMMENT '样式属性',
  `list_class` varchar(20) DEFAULT NULL COMMENT '列表回显样式',
  `sort` tinyint(3) NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `create_by` varchar(20) DEFAULT '' COMMENT '创建人(数据归属人)，存储登录名',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(20) DEFAULT '' COMMENT '更新人，存储登录名',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UNIDX_VALUE_TYPE` (`dict_type`,`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统字典数据表';


-- ----------------------------
-- Records of t_sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_dict` VALUES (null, '1', '男', 'sys_user_sex', NULL, NULL, 1, '1001', 1, 'admin', '2023-02-13 14:04:40', 'admin1', '2023-04-11 17:16:20', NULL);
INSERT INTO `t_sys_dict` VALUES (null, '0', '女', 'sys_user_sex', NULL, NULL, 1, '1001', 1, 'admin', '2023-02-13 14:04:40', '', '2023-04-11 17:16:20', NULL);
INSERT INTO `t_sys_dict` VALUES (null, '1', '启用', 'sys_status', NULL, 'default', 1, '1001', 1, 'admin', '2023-02-13 18:03:37', 'yxkong', '2023-04-19 18:52:03', NULL);
INSERT INTO `t_sys_dict` VALUES (null, '0', '停用', 'sys_status', NULL, 'warning', 1, '1001', 1, 'admin', '2023-02-13 18:03:37', 'yxkong', '2023-04-19 18:52:24', NULL);
INSERT INTO `t_sys_dict` VALUES (null, '0', '租户数据权限', 'sys_datascope', NULL, NULL, 1, '1001', 1, 'admin', '2023-02-14 15:51:06', '', '2023-04-11 17:16:20', NULL);
INSERT INTO `t_sys_dict` VALUES (null, '1', '本部门数据权限', 'sys_datascope', NULL, NULL, 1, '1001', 1, 'admin', '2023-02-14 15:51:06', '', '2023-04-11 17:16:20', NULL);
INSERT INTO `t_sys_dict` VALUES (null, '2', '本部门及下级部门数据权限', 'sys_datascope', NULL, NULL, 1, '1001', 1, 'admin', '2023-02-14 15:51:06', '', '2023-04-11 17:16:20', NULL);
INSERT INTO `t_sys_dict` VALUES (null, '3', '本人数据权', 'sys_datascope', NULL, NULL, 1, '1001', 1, 'admin', '2023-02-14 15:51:06', '', '2023-04-11 17:16:20', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'str', '字符', 'sys_char_type', NULL, 'default', 1, '1001', 1, 'admin', '2023-04-12 16:30:07', 'admin', '2023-04-23 16:14:35', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'int', '整数', 'sys_char_type', NULL, 'warning', 2, '1001', 1, 'admin', '2023-04-12 16:31:30', 'yxkong', '2023-04-23 15:41:00', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'default', '蓝色', 'sys_list_class', NULL, 'default', 1, '1001', 1, 'admin', '2023-04-19 18:03:03', 'yxkong', '2023-07-14 14:20:03', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'success', '绿色', 'sys_list_class', NULL, 'success', 1, '1001', 1, 'admin', '2023-04-19 18:03:59', 'yxkong', '2023-07-14 14:19:18', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'info', '灰色', 'sys_list_class', NULL, 'info', 1, '1001', 1, 'admin', '2023-04-19 18:04:11', 'yxkong', '2023-07-14 14:20:28', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'warning', '褐色', 'sys_list_class', NULL, 'warning', 1, '1001', 1, 'admin', '2023-04-19 18:04:33', 'yxkong', '2023-07-14 14:20:47', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'danger', '红色', 'sys_list_class', NULL, 'danger', 1, '1001', 1, 'admin', '2023-04-19 18:05:06', 'yxkong', '2023-07-14 14:21:07', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'sys_status', '状态', 'sys_gen_code_dict', NULL, 'success', 1, '1001', 1, 'yxkong', '2023-05-05 12:25:33', 'yxkong', '2023-05-15 19:01:02', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'input', '文本框', 'sys_form_type', NULL, 'info', 0, '1001', 1, 'yxkong', '2023-05-05 17:14:13', 'yxkong', '2023-05-05 17:49:30', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'textarea', '文本域', 'sys_form_type', NULL, 'info', 1, '1001', 1, 'yxkong', '2023-05-05 17:26:11', '', '2023-05-05 17:26:11', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'select', '下拉框', 'sys_form_type', NULL, 'info', 2, '1001', 1, 'yxkong', '2023-05-05 17:26:33', '', '2023-05-05 17:26:33', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'radio', '单选框', 'sys_form_type', NULL, 'info', 3, '1001', 1, 'yxkong', '2023-05-05 17:26:53', '', '2023-05-05 17:26:53', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'checkbox', '复选框', 'sys_form_type', NULL, 'info', 4, '1001', 1, 'yxkong', '2023-05-05 17:50:02', 'yxkong', '2023-05-05 17:50:17', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'datetime', '日期控件', 'sys_form_type', NULL, 'info', 5, '1001', 1, 'yxkong', '2023-05-05 17:50:36', '', '2023-05-05 17:50:36', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'fileUpload', '文件上传', 'sys_form_type', NULL, 'info', 6, '1001', 1, 'yxkong', '2023-05-05 17:51:35', '', '2023-05-05 17:51:35', NULL);
INSERT INTO `t_sys_dict` VALUES (null, '=', '等于', 'sys_query_type', NULL, 'info', 1, '1001', 1, 'yxkong', '2023-05-05 17:56:28', '', '2023-05-05 17:56:28', NULL);
INSERT INTO `t_sys_dict` VALUES (null, '!=', '不等于', 'sys_query_type', NULL, 'info', 2, '1001', 1, 'yxkong', '2023-05-05 17:56:44', '', '2023-05-05 17:56:44', NULL);
INSERT INTO `t_sys_dict` VALUES (null, '>', '大于', 'sys_query_type', NULL, 'info', 3, '1001', 1, 'yxkong', '2023-05-05 17:57:01', '', '2023-05-05 17:57:01', NULL);
INSERT INTO `t_sys_dict` VALUES (null, '>=', '大于等于', 'sys_query_type', NULL, 'info', 4, '1001', 1, 'yxkong', '2023-05-05 17:57:14', '', '2023-05-05 17:57:14', NULL);
INSERT INTO `t_sys_dict` VALUES (null, '<', '小于', 'sys_query_type', NULL, 'info', 5, '1001', 1, 'yxkong', '2023-05-05 17:57:35', '', '2023-05-05 17:57:35', NULL);
INSERT INTO `t_sys_dict` VALUES (null, '<=', '小于等于', 'sys_query_type', NULL, 'info', 6, '1001', 1, 'yxkong', '2023-05-05 17:57:50', 'yxkong', '2023-05-05 17:57:57', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'like', '模糊', 'sys_query_type', NULL, 'info', 7, '1001', 1, 'yxkong', '2023-05-05 17:58:19', '', '2023-05-05 17:58:19', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'between', '区间', 'sys_query_type', NULL, 'info', 8, '1001', 1, 'yxkong', '2023-05-05 17:58:40', '', '2023-05-05 17:58:40', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'daterange', '时间范围', 'sys_form_type', NULL, 'info', 7, '1001', 1, 'yxkong', '2023-05-10 16:16:54', 'yxkong', '2023-05-10 16:17:12', NULL);
INSERT INTO `t_sys_dict` VALUES (null, '1', '同步', 'ops_ call_sync', NULL, 'info', 1, '1001', 1, 'yxkong', '2023-05-15 18:11:01', '', '2023-05-15 18:11:01', NULL);
INSERT INTO `t_sys_dict` VALUES (null, '0', '异步', 'ops_ call_sync', NULL, 'info', 2, '1001', 1, 'yxkong', '2023-05-15 18:11:09', '', '2023-05-15 18:11:09', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'ops_call_type', '调用方式', 'sys_gen_code_dict', NULL, 'info', 7, '1001', 1, 'yxkong', '2023-05-15 18:14:12', 'yxkong', '2023-05-15 19:15:19', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'ops_call_sync', '调用同步状态', 'sys_gen_code_dict', NULL, 'info', 2, '1001', 1, 'yxkong', '2023-05-15 18:14:34', '', '2023-05-17 15:59:30', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'ops_http_mode', '请求模式', 'sys_gen_code_dict', NULL, 'info', 3, '1001', 1, 'yxkong', '2023-05-15 18:14:51', '', '2023-05-15 18:14:51', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'ops_content_type', '请求内容类型', 'sys_gen_code_dict', NULL, 'info', 4, '1001', 1, 'yxkong', '2023-05-15 18:15:38', '', '2023-05-15 18:15:38', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'ops_show_type', '展示状态', 'sys_gen_code_dict', NULL, 'info', 8, '1001', 1, 'yxkong', '2023-05-15 19:00:50', 'yxkong', '2023-05-15 19:15:27', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'ops_param_source', '参数来源', 'sys_gen_code_dict', NULL, 'info', 6, '1001', 1, 'yxkong', '2023-05-15 19:15:00', 'yxkong', '2023-05-15 19:15:09', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'ops_param_type', '参数类型', 'sys_gen_code_dict', NULL, 'info', 9, '1001', 1, 'yxkong', '2023-05-15 19:15:44', '', '2023-05-15 19:15:44', NULL);
INSERT INTO `t_sys_dict` VALUES (null, '0', '外包', 'sys_user_type', NULL, 'info', 1, '1001', 1, 'yxkong', '2023-05-31 15:49:36', '', '2023-05-31 15:49:36', NULL);
INSERT INTO `t_sys_dict` VALUES (null, '1', '正编', 'sys_user_type', NULL, 'info', 1, '1001', 1, 'yxkong', '2023-05-31 15:49:47', '', '2023-05-31 15:49:47', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'reg', '正常注册', 'sys_user_channel', NULL, 'info', 1, '1001', 1, 'yxkong', '2023-05-31 15:52:13', '', '2023-06-01 17:27:20', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'add', '后台添加', 'sys_user_channel', NULL, 'info', 1, '1001', 1, 'yxkong', '2023-05-31 15:52:37', '', '2023-06-01 17:27:16', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'ldap', 'ldap', 'sys_user_channel', NULL, 'info', 1, '1001', 1, 'yxkong', '2023-05-31 15:53:00', '', '2023-06-01 17:27:12', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'wx', '微信', 'sys_user_channel', NULL, 'info', 1, '1001', 1, 'yxkong', '2023-05-31 15:53:19', '', '2023-06-01 17:27:02', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'ding', '钉钉', 'sys_user_channel', NULL, 'info', 1, '1001', 1, 'yxkong', '2023-05-31 15:53:39', '', '2023-06-01 17:27:25', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'sys_user_type', '用户类型', 'sys_gen_code_dict', NULL, 'info', 2, '1001', 1, 'yxkong', '2023-05-31 15:55:09', 'yxkong', '2023-05-31 15:55:24', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'sys_user_channel', '用户来源', 'sys_gen_code_dict', NULL, 'info', 1, '1001', 1, 'yxkong', '2023-05-31 15:55:39', '', '2023-06-01 17:27:33', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'normal', 'normal', 'sys_user_channel', NULL, 'info', 1, '1001', 1, 'yxkong', '2023-06-13 16:45:55', '', '2023-06-13 16:45:55', NULL);
INSERT INTO `t_sys_dict` VALUES (null, '0', '系统', 'sys_classify', NULL, 'success', 1, '1001', 1, 'yxkong', '2023-07-05 16:44:35', '', '2023-07-05 16:44:35', NULL);
INSERT INTO `t_sys_dict` VALUES (null, '1', '业务', 'sys_classify', NULL, 'danger', 1, '1001', 1, 'yxkong', '2023-07-05 16:44:45', '', '2023-07-05 16:44:45', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'login', '登录节点', 'sys_sms_node', NULL, 'info', 1, '1001', 1, 'yxkong', '2023-07-11 19:00:50', '', '2023-07-11 19:00:50', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'pm', '项目流程', 'flow_process_type', NULL, 'default', 1, 1001, 1, 'admin', '2023-10-10 10:40:21', 'yxkong', '2023-11-21 17:23:54', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'oa', 'OA', 'flow_process_type', NULL, 'danger', 1, 1001, 1, 'admin', '2023-11-01 11:27:09', 'yxkong', '2023-11-21 17:24:41', NULL);
INSERT INTO `t_sys_dict` VALUES (null, 'assess', '评审会', 'pm_task_type', NULL, 'default', 3, 1001, 1, 'yxkong', '2023-12-20 17:35:14', 'yxkong', '2023-12-20 17:43:45', NULL);
INSERT INTO `t_sys_dict`  VALUES (null, 'checkAccept', '业务验收', 'pm_task_type', NULL, 'default', 8, 1001, 1, 'yxkong', '2023-12-20 17:45:30', '', '2023-12-20 17:45:30', NULL);
INSERT INTO `t_sys_dict`  VALUES (null, 'commonWork', '公共事务', 'pm_task_type', NULL, 'default', 0, NULL, 1, 'yxkong', '2023-12-28 14:04:06', '', '2023-12-28 14:04:06', NULL);
INSERT INTO `t_sys_dict`  VALUES (null, 'design', '设计', 'pm_task_type', NULL, 'success', 4, 1001, 1, 'yxkong', '2023-12-08 12:34:40', 'yxkong', '2023-12-20 17:43:41', NULL);
INSERT INTO `t_sys_dict`  VALUES (null, 'dev', '开发', 'pm_task_type', NULL, 'success', 5, 1001, 1, 'yxkong', '2023-12-08 12:34:28', 'yxkong', '2023-12-20 17:43:38', NULL);
INSERT INTO `t_sys_dict`  VALUES (null, 'jointDebug', '联调', 'pm_task_type', NULL, 'default', 6, 1001, 1, 'yxkong', '2023-12-20 17:37:53', 'yxkong', '2023-12-20 17:43:34', NULL);
INSERT INTO `t_sys_dict`  VALUES (null, 'online', '上线', 'pm_task_type', NULL, 'default', 9, 1001, 1, 'yxkong', '2023-12-20 17:46:31', '', '2023-12-20 17:46:31', NULL);
INSERT INTO `t_sys_dict`  VALUES (null, 'support', '支持', 'pm_task_type', NULL, 'danger', 1, 1001, 1, 'yxkong', '2023-12-08 12:35:15', '', '2023-12-08 12:35:15', NULL);
INSERT INTO `t_sys_dict`  VALUES (null, 'test', '测试', 'pm_task_type', NULL, 'success', 7, 1001, 1, 'yxkong', '2023-12-08 12:35:27', 'yxkong', '2023-12-20 17:43:31', NULL);
INSERT INTO `t_sys_dict`  VALUES (null, 'writeDoc', '编写文档', 'pm_task_type', NULL, 'default', 2, 1001, 1, 'yxkong', '2023-12-20 17:40:14', '', '2023-12-20 17:40:14', NULL);
INSERT INTO `t_sys_dict`  VALUES (null, 'bedDev', '后端排期', 'pm_schedule_node', NULL, 'default', 1, 1001, 1, 'yxkong', '2023-12-20 16:36:14', '', '2023-12-20 16:36:14', NULL);
INSERT INTO `t_sys_dict`  VALUES (null, 'fedDev', '前端排期', 'pm_schedule_node', NULL, 'default', 1, 1001, 1, 'yxkong', '2023-12-20 16:35:59', '', '2023-12-20 16:35:59', NULL);
INSERT INTO `t_sys_dict`  VALUES (null, 'jointDebug', '联调排期', 'pm_schedule_node', NULL, 'default', 1, 1001, 1, 'yxkong', '2023-12-20 16:36:54', '', '2023-12-20 16:36:54', NULL);
INSERT INTO `t_sys_dict`  VALUES (null, 'qaCaseTest', '测试用例排期', 'pm_schedule_node', 'default', 'default', 1, 1001, 1, 'yxkong', '2023-12-20 17:31:34', 'yxkong', '2023-12-20 17:32:24', NULL);
INSERT INTO `t_sys_dict`  VALUES (null, 'qaTest', '测试排期', 'pm_schedule_node', NULL, 'danger', 1, 1001, 1, 'yxkong', '2023-12-20 16:36:40', '', '2023-12-20 16:36:40', NULL);
COMMIT;



-- ----------------------------
-- Table structure for t_sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_job_log`;
CREATE TABLE `t_sys_job_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(100) NOT NULL COMMENT '处理器bean名称',
  `handler_param` varchar(100) DEFAULT NULL COMMENT '处理器参数',
  `start_time` datetime DEFAULT NULL COMMENT '开始执行时间',
  `end_date` datetime DEFAULT NULL COMMENT '结束执行时间',
  `execute_id` varchar(32) DEFAULT NULL COMMENT '执行id',
  `execute_num` int(8) DEFAULT NULL COMMENT 'execute_id的第n次执行',
  `execute_time` int(8) DEFAULT '0' COMMENT '执行耗时，毫秒',
  `result` varchar(2000) DEFAULT '' COMMENT '执行结果',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0异常）',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(20) DEFAULT NULL COMMENT '创建人（保存登录名称）',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '更新人',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15255 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='任务执行日志表';


-- ----------------------------
-- Table structure for t_sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_login_log`;
CREATE TABLE `t_sys_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `request_ip` varchar(128) DEFAULT '' COMMENT '登录IP地址',
  `login_way` varchar(10) DEFAULT '' COMMENT '登录方式',
  `login_location` varchar(200) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `token` varchar(50) DEFAULT '' COMMENT '登录token',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` varchar(5) DEFAULT '0' COMMENT '登录状态',
  `message` varchar(500) DEFAULT '' COMMENT '提示消息',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  `create_by` varchar(20) DEFAULT '' COMMENT '登录账户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='系统访问记录';



-- ----------------------------
-- Table structure for t_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单ID',
  `sort` int(4) DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(100) DEFAULT '' COMMENT '路由地址',
  `component` varchar(128) DEFAULT NULL COMMENT '组件路径',
  `query` varchar(100) DEFAULT NULL COMMENT '路由参数',
  `frame` tinyint(1) DEFAULT '1' COMMENT '外链状态（0外链 1非外链）',
  `cache` tinyint(1) DEFAULT '0' COMMENT '缓存状态（0缓存 1不缓存）',
  `type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` tinyint(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `give_tenant` tinyint(1) DEFAULT '1' COMMENT '赋予租户状态(0 不赋予,1 赋予)',
  `perms` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(50) DEFAULT '' COMMENT '菜单图标',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `create_by` varchar(20) DEFAULT '' COMMENT '创建人(数据归属人)，存储登录名',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(20) DEFAULT '' COMMENT '更新人，存储登录名',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='菜单权限表';

-- ----------------------------
-- Records of t_sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, '', 0, 0, 'D', 1, 0, '', 'system', 1001, 1, 'admin', '2023-02-10 15:26:09', 'admin', '2023-04-24 11:21:49', '');
INSERT INTO `t_sys_menu` VALUES (2, '用户管理', 1, 2, 'user', 'system/user/index', '', 0, 0, 'M', 1, 0, 'sys/user/query', 'user', 1001, 1, 'admin', '2023-02-10 15:34:02', 'admin', '2023-04-23 19:13:01', '');
INSERT INTO `t_sys_menu` VALUES (3, '角色管理', 1, 2, 'role', 'system/role/index', '', 0, 0, 'M', 1, 0, 'sys/role/query', 'peoples', 1001, 1, 'admin', '2023-02-10 15:35:23', 'admin', '2023-04-24 11:22:52', '');
INSERT INTO `t_sys_menu` VALUES (4, '部门管理', 1, 3, 'dept', 'system/dept/index', '', 0, 0, 'M', 1, 0, 'sys/dept/query', 'tree', 1001, 1, 'admin', '2023-02-10 15:35:23', 'admin', '2023-04-24 11:23:08', '');
INSERT INTO `t_sys_menu` VALUES (5, '菜单管理', 1, 4, 'menu', 'system/menu/index', NULL, 0, 0, 'M', 1, 0, 'sys/menu/query', 'tree-table', 1001, 1, 'admin', '2023-02-25 10:12:57', 'admin', '2023-04-24 11:23:20', '');
INSERT INTO `t_sys_menu` VALUES (6, '字典管理', 1, 5, 'dictType', 'system/dict/type/index', '', 0, 0, 'M', 1, 0, 'sys/dict/type/query', 'dict', 1001, 1, 'admin', '2023-02-10 15:35:23', 'admin', '2023-04-24 11:23:48', '');
INSERT INTO `t_sys_menu` VALUES (7, '字典数据', 1, 6, 'dict', 'system/dict/index', '', 0, 0, 'M', 0, 0, 'sys/dict/query', 'documentation', 1001, 1, 'admin', '2023-02-10 15:35:23', 'admin', '2023-04-24 11:23:59', '');
INSERT INTO `t_sys_menu` VALUES (8, '配置管理', 1, 7, 'configs', 'system/config/index', NULL, 0, 0, 'M', 1, 0, 'sys/config/query', 'edit', 1001, 1, '', '2023-04-07 17:14:42', 'admin', '2023-04-24 11:24:14', '');
INSERT INTO `t_sys_menu` VALUES (9, '缓存日志', 122, 8, 'cacheMonitor', 'monitor/cachelog/index', NULL, 0, 0, 'M', 1, 0, 'sys/monitor/cacheLog/query', 'druid', 1001, 1, 'admin', '2023-03-08 15:48:49', 'yxkong', '2023-05-08 16:09:32', '');
INSERT INTO `t_sys_menu` VALUES (10, '系统日志', 122, 9, 'cache', 'monitor/optlog/index', NULL, 0, 0, 'M', 1, 0, 'sys/monitor/optLog/query', 'redis', 1001, 1, 'admin', '2023-03-08 16:46:36', 'yxkong', '2023-05-08 16:12:01', '');
INSERT INTO `t_sys_menu` VALUES (11, '任务管理', 1, 7, 'schedule', 'system/job/index', NULL, 0, 0, 'M', 1, 0, 'sys/job/query', 'job', 1001, 1, 'admin', '2023-03-07 12:10:15', 'admin', '2023-04-24 11:24:59', '');
INSERT INTO `t_sys_menu` VALUES (101, '新增', 2, 2, '#', '', '', 0, 0, 'B', 1, 0, 'sys/user/add', '', 1001, 1, 'admin', '2023-02-10 15:38:47', '', '2023-04-13 20:44:35', '');
INSERT INTO `t_sys_menu` VALUES (102, '修改', 2, 3, '#', '', '', 0, 0, 'B', 1, 0, 'sys/user/edit', '', 1001, 1, 'admin', '2023-02-10 15:38:47', '', '2023-04-13 20:44:35', '');
INSERT INTO `t_sys_menu` VALUES (103, '重置密码', 2, 7, '#', '', '', 0, 0, 'B', 1, 0, 'sys/user/reset', '', 1001, 1, 'admin', '2023-02-10 15:38:47', '', '2023-04-13 20:44:35', '');
INSERT INTO `t_sys_menu` VALUES (104, '新增', 3, 2, '#', '', '', 0, 0, 'B', 1, 0, 'sys/role/add', '', 1001, 1, 'admin', '2023-02-10 15:39:53', '', '2023-04-13 20:44:35', '');
INSERT INTO `t_sys_menu` VALUES (105, '修改', 3, 3, '#', '', '', 0, 0, 'B', 1, 0, 'sys/role/modify', '', 1001, 1, 'admin', '2023-02-10 15:39:53', '', '2023-04-13 20:44:35', '');
INSERT INTO `t_sys_menu` VALUES (106, '删除', 3, 4, '#', '', '', 0, 0, 'B', 1, 0, 'sys/role/delete', '', 1001, 1, 'admin', '2023-02-10 15:39:53', '', '2023-04-13 20:44:35', '');
INSERT INTO `t_sys_menu` VALUES (107, '角色详情查询', 3, 5, '#', '', '', 0, 0, 'B', 1, 0, 'sys/role/queryById', '', 1001, 1, 'admin', '2023-02-10 15:39:53', '', '2023-04-13 20:44:35', '');
INSERT INTO `t_sys_menu` VALUES (108, '新增', 4, 2, '#', '', '', 0, 0, 'B', 1, 0, 'sys/dept/add', '', 1001, 1, 'admin', '2023-02-10 15:40:33', '', '2023-04-13 20:44:35', '');
INSERT INTO `t_sys_menu` VALUES (109, '修改', 4, 3, '#', '', '', 0, 0, 'B', 1, 0, 'sys/dept/modify', '', 1001, 1, 'admin', '2023-02-10 15:40:33', '', '2023-04-13 20:44:35', '');
INSERT INTO `t_sys_menu` VALUES (110, '删除', 4, 4, '#', '', '', 0, 0, 'B', 1, 0, 'sys/dept/delete', '', 1001, 1, 'admin', '2023-02-10 15:40:33', '', '2023-04-13 20:44:35', '');
INSERT INTO `t_sys_menu` VALUES (111, '获取部门树列表', 4, 5, '#', '', '', 0, 0, 'B', 1, 0, 'sys/dept/deptTree', '', 1001, 1, 'admin', '2023-02-10 15:40:33', '', '2023-04-13 20:44:35', '');
INSERT INTO `t_sys_menu` VALUES (113, '新增', 6, 2, '#', '', '', 0, 0, 'B', 1, 0, 'sys/dict/type/add', '', 1001, 1, 'admin', '2023-02-10 15:42:05', '', '2023-04-13 20:44:35', '');
INSERT INTO `t_sys_menu` VALUES (114, '修改', 6, 3, '#', '', '', 0, 0, 'B', 1, 0, 'sys/dict/type/modify', '', 1001, 1, 'admin', '2023-02-10 15:42:05', '', '2023-04-13 20:44:35', '');
INSERT INTO `t_sys_menu` VALUES (115, '删除', 6, 4, '#', '', '', 0, 0, 'B', 1, 0, 'sys/dict/type/delete', '', 1001, 1, 'admin', '2023-02-10 15:42:05', '', '2023-04-13 20:44:35', '');
INSERT INTO `t_sys_menu` VALUES (116, '重载', 6, 5, '#', '', '', 0, 0, 'B', 1, 0, 'sys/dict/type/reload', '', 1001, 1, 'admin', '2023-02-10 15:42:05', '', '2023-04-13 20:44:35', '');
INSERT INTO `t_sys_menu` VALUES (117, '查询', 7, 1, '#', '', '', 0, 0, 'B', 1, 0, 'sys/dict/findByType', '', 1001, 1, 'admin', '2023-02-10 15:42:05', '', '2023-04-13 20:44:35', '');
INSERT INTO `t_sys_menu` VALUES (118, '新增', 7, 2, '#', '', '', 0, 0, 'B', 1, 0, 'sys/dict/add', '', 1001, 1, 'admin', '2023-02-10 15:42:05', '', '2023-04-13 20:44:35', '');
INSERT INTO `t_sys_menu` VALUES (119, '删除', 7, 4, '#', '', '', 0, 0, 'B', 1, 0, 'sys/dict/delete', '', 1001, 1, 'admin', '2023-02-10 15:42:05', '', '2023-04-13 20:44:35', '');
INSERT INTO `t_sys_menu` VALUES (122, '系统监控', 0, 2, 'monitor', NULL, NULL, 0, 0, 'D', 1, 0, NULL, 'monitor', 1001, 1, 'yxkong', '2023-04-12 18:35:09', 'admin', '2023-04-24 11:22:06', '');
INSERT INTO `t_sys_menu` VALUES (125, '系统工具', 0, 3, 'tool', NULL, NULL, 0, 0, 'D', 1, 0, NULL, 'code', 1001, 1, 'yxkong', '2023-04-12 19:15:46', 'admin', '2023-04-27 21:24:00', '');
INSERT INTO `t_sys_menu` VALUES (126, '代码生成', 125, 1, 'gen', 'tool/gen/index', NULL, 0, 0, 'M', 1, 0, 'tool/gen/', 'code', 1001, 1, 'yxkong', '2023-04-12 19:16:59', 'admin', '2023-04-27 16:59:16', '');
INSERT INTO `t_sys_menu` VALUES (129, '灰度管理', 1, 7, 'gray', 'system/gray/index', NULL, 0, 0, 'M', 1, 0, 'sys/gray/query', 'slider', 1001, 1, 'yxkong', '2023-04-18 17:16:49', 'admin', '2023-07-13 11:29:13', '');
INSERT INTO `t_sys_menu` VALUES (130, '代码生成配置', 125, 2, 'gen/detail', 'tool/gen/detail/index', NULL, 0, 0, 'M', 0, 0, 'tool/gen/detail', 'component', 1001, 1, 'admin', '2023-04-27 21:26:02', 'yxkong', '2023-05-08 16:12:13', '');
INSERT INTO `t_sys_menu` VALUES (131, '业务工具', 0, 1, 'business', NULL, NULL, 0, 0, 'D', 1, 0, NULL, 'business-tools', 1001, 1, 'admin', '2023-05-16 15:53:09', 'admin', '2023-07-04 11:07:02', '');
INSERT INTO `t_sys_menu` VALUES (132, '数据查询', 131, 1, 'dataQuery', 'ops/data-query/index', NULL, 0, 0, 'M', 1, 0, 'sys/ops/interface/dataQuery', 'query-data', 1001, 1, 'admin', '2023-05-16 15:56:12', 'admin', '2023-07-21 08:05:38', '');
INSERT INTO `t_sys_menu` VALUES (133, '接口维护', 131, 2, 'interface', 'ops/interface/index', NULL, 0, 0, 'M', 1, 0, 'sys/ops/interface/query', 'edit', 1001, 1, 'yxkong', '2023-05-17 17:28:27', 'admin', '2023-07-23 08:18:36', '');
INSERT INTO `t_sys_menu` VALUES (134, 'Icon列表', 125, 1, 'icon', 'tool/icon/index', NULL, 0, 0, 'M', 1, 0, 'showIcon', 'button', 1001, 1, 'admin', '2023-05-26 18:29:47', 'admin', '2023-05-26 18:35:01', '');
INSERT INTO `t_sys_menu` VALUES (135, '综合数据查询', 131, 1, 'queryMixData', 'ops/data-query-mix/index', NULL, 0, 0, 'M', 1, 0, 'sys/ops/interface/queryMixData', 'query-data', 1001, 1, 'admin', '2023-07-13 11:28:59', 'admin', '2023-07-19 18:58:38', '');
INSERT INTO `t_sys_menu` VALUES (136, '数据源配置', 131, 3, 'dbconfig', 'ops/dbconfig/index', NULL, 0, 0, 'M', 1, 0, 'sys/ops/dbConfig/query', 'data-line', 1001, 1, 'yxkong', '2023-07-20 09:40:05', 'admin', '2023-07-23 08:18:59', '');
INSERT INTO `t_sys_menu` VALUES (137, '数据库脚本', 131, 3, 'dbscript', 'ops/dbscript/index', NULL, 0, 0, 'M', 1, 0, 'sys/ops/dbScript/query', 'build', 1001, 1, 'yxkong', '2023-07-20 09:41:37', 'admin', '2023-07-23 08:19:11', '');
INSERT INTO `t_sys_menu` VALUES (138, '脚本查询', 131, 1, 'dataQueryScript', 'ops/data-query-script/index', NULL, 0, 0, 'M', 1, 0, 'sys/ops/dbScript/scriptQuery', 'query-data', 1001, 1, 'yxkong', '2023-07-20 09:42:28', 'admin', '2023-07-21 07:50:07', '');
INSERT INTO `t_sys_menu` VALUES (139, '三方用户', 1, 1, 'thirdUser', 'system/user/thirdUser', NULL, 0, 0, 'M', 1, 0, 'sys/third/user/query', 'user', 1001, 1, 'yxkong', '2023-07-20 09:43:34', '', '2023-07-20 09:43:34', '');
INSERT INTO `t_sys_menu` VALUES (140, '用户列表', 1, 9, 'roleQuery', 'system/user/roleQuery', NULL, 0, 0, 'M', 0, 0, 'sys/user/roleQuery', '', 1001, 1, 'admin', '2023-07-21 08:47:16', '', '2023-07-21 08:47:16', '');
INSERT INTO `t_sys_menu` VALUES (141, '新增', 133, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/ops/interface/add', '', 1001, 1, 'admin', '2023-07-23 08:05:28', '', '2023-07-23 08:05:28', '');
INSERT INTO `t_sys_menu` VALUES (142, '编辑', 133, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/ops/interface/modify', '', 1001, 1, 'admin', '2023-07-23 08:06:09', 'admin', '2023-07-23 08:15:33', '');
INSERT INTO `t_sys_menu` VALUES (143, '删除', 133, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/ops/interface/delete', '', 1001, 1, 'admin', '2023-07-23 08:06:37', '', '2023-07-23 08:06:37', '');
INSERT INTO `t_sys_menu` VALUES (144, '明细', 133, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/ops/interface/detail', '', 1001, 1, 'admin', '2023-07-23 08:07:08', '', '2023-07-23 08:07:08', '');
INSERT INTO `t_sys_menu` VALUES (145, '新增', 136, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/ops/dbConfig/add', '', 1001, 1, 'admin', '2023-07-23 08:10:20', '', '2023-07-23 08:10:20', '');
INSERT INTO `t_sys_menu` VALUES (146, '编辑', 136, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/ops/dbConfig/modify', 'build', 1001, 1, 'admin', '2023-07-23 08:11:06', 'admin', '2023-07-23 08:15:17', '');
INSERT INTO `t_sys_menu` VALUES (147, '修改密码', 136, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/ops/dbConfig/modifyPwd', '', 1001, 1, 'admin', '2023-07-23 08:11:40', '', '2023-07-23 08:11:40', '');
INSERT INTO `t_sys_menu` VALUES (148, '删除', 136, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/ops/dbConfig/delete', '', 1001, 1, 'admin', '2023-07-23 08:12:15', '', '2023-07-23 08:12:15', '');
INSERT INTO `t_sys_menu` VALUES (149, '新增', 137, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/ops/dbScript/add', '', 1001, 1, 'admin', '2023-07-23 08:12:58', '', '2023-07-23 08:12:58', '');
INSERT INTO `t_sys_menu` VALUES (150, '编辑', 137, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/ops/dbScript/modify', '', 1001, 1, 'admin', '2023-07-23 08:14:02', '', '2023-07-23 08:14:02', '');
INSERT INTO `t_sys_menu` VALUES (151, '删除', 137, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/ops/dbScript/delete', '', 1001, 1, 'admin', '2023-07-23 08:14:48', '', '2023-07-23 08:14:48', '');
INSERT INTO `t_sys_menu` VALUES (152, '明细', 136, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/ops/dbConfig/detail', '', 1001, 1, 'admin', '2023-07-23 08:18:07', '', '2023-07-23 08:18:07', '');
INSERT INTO `t_sys_menu` VALUES (153, '明细', 137, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/ops/dbScript/detail', '', 1001, 1, 'admin', '2023-07-24 03:13:08', '', '2023-07-24 03:13:08', '');
INSERT INTO `t_sys_menu` VALUES (154, '定制化', 0, 1, 'customize', NULL, NULL, 0, 0, 'D', 1, 0, NULL, 'business-tools', NULL, 1, 'haodanyang', '2023-10-17 06:30:18', '', '2023-10-17 06:30:18', '');
INSERT INTO `t_sys_menu` VALUES (155, '参数配置', 154, 1, 'config', 'customize/config/index', NULL, 0, 0, 'M', 1, 0, 'customize/config/query', 'button', NULL, 1, 'haodanyang', '2023-10-17 06:31:24', '', '2023-10-17 06:31:24', '');
INSERT INTO `t_sys_menu` VALUES (156, '转换配置', 154, 1, 'transform', 'customize/transform/index', NULL, 0, 0, 'M', 1, 0, 'customize/transform/query', 'cascader', NULL, 1, 'haodanyang', '2023-10-18 06:50:02', '', '2023-10-18 06:50:02', '');
INSERT INTO `t_sys_menu` VALUES (157, '映射配置', 154, 1, 'mappingtype', 'customize/mappingtype/index', NULL, 0, 0, 'M', 1, 0, 'customize/transform/dicttype/query', 'component', NULL, 1, 'haodanyang', '2023-10-18 06:50:51', '', '2023-10-18 06:50:51', '');
INSERT INTO `t_sys_menu` VALUES (158, '映射字典', 154, 1, 'mappingdict', 'customize/mappingdict/index', NULL, 0, 0, 'M', 1, 0, 'customize/transform/dict/query', 'code', NULL, 1, 'haodanyang', '2023-10-18 06:51:33', '', '2023-10-18 06:51:33', '');
INSERT INTO `t_sys_menu` VALUES (159, '新增修改', 155, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'customize/transform/channelConfig/insertUpdateConfig', '', NULL, 1, 'zhangna2', '2023-11-07 08:50:16', '', '2023-11-07 08:50:16', '');
INSERT INTO `t_sys_menu` VALUES (160, '新增', 158, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'customize/transform/dict/add', '', NULL, 1, 'zhangna2', '2023-11-07 08:50:51', '', '2023-11-07 08:50:51', '');
INSERT INTO `t_sys_menu` VALUES (161, '修改', 158, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'customize/transform/dict/modify', '', NULL, 1, 'zhangna2', '2023-11-07 08:51:08', '', '2023-11-07 08:51:08', '');
INSERT INTO `t_sys_menu` VALUES (162, '删除', 158, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'customize/transform/dict/delete', '', NULL, 1, 'zhangna2', '2023-11-07 08:51:24', '', '2023-11-07 08:51:24', '');
INSERT INTO `t_sys_menu` VALUES (163, '修改', 157, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'customize/transform/dicttype/modify', '', NULL, 1, 'zhangna2', '2023-11-07 08:51:58', '', '2023-11-07 08:51:58', '');
INSERT INTO `t_sys_menu` VALUES (164, '新增', 157, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'customize/transform/dicttype/add', '', NULL, 1, 'zhangna2', '2023-11-07 08:52:11', '', '2023-11-07 08:52:11', '');
INSERT INTO `t_sys_menu` VALUES (165, '删除', 157, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'customize/transform/dicttype/delete', '', NULL, 1, 'zhangna2', '2023-11-07 08:52:27', '', '2023-11-07 08:52:27', '');
INSERT INTO `t_sys_menu` VALUES (166, '删除', 156, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'customize/transform/delete', '', NULL, 1, 'zhangna2', '2023-11-07 08:53:10', '', '2023-11-07 08:53:10', '');
INSERT INTO `t_sys_menu` VALUES (167, '修改', 156, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'customize/transform/modify', '', NULL, 1, 'zhangna2', '2023-11-07 08:53:26', '', '2023-11-07 08:53:26', '');
INSERT INTO `t_sys_menu` VALUES (168, '新增', 156, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'customize/transform/add', '', NULL, 1, 'zhangna2', '2023-11-07 08:53:39', '', '2023-11-07 08:53:39', '');
INSERT INTO `t_sys_menu` VALUES (169, '查看', 156, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'customize/transform/detail', '', NULL, 1, 'zhangna2', '2023-11-07 08:53:55', '', '2023-11-07 08:53:55', '');
INSERT INTO `t_sys_menu` VALUES (170, '验证', 156, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'customize/transform/validate', '', NULL, 1, 'zhangna2', '2023-11-07 08:54:50', '', '2023-11-07 08:54:50', '');
INSERT INTO `t_sys_menu` VALUES (180, '状态管理', 2, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/user/status', '', 1001, 1, 'yxkong', '2023-12-28 10:58:24', '', '2023-12-28 10:58:24', '');
INSERT INTO `t_sys_menu` VALUES (181, '修改', 139, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/third/user/modify', '', 1001, 1, 'yxkong', '2023-12-28 10:59:30', '', '2023-12-28 10:59:30', '');
INSERT INTO `t_sys_menu` VALUES (182, '审批', 139, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/third/user/approve', '', 1001, 1, 'yxkong', '2023-12-28 11:00:00', '', '2023-12-28 11:00:00', '');
INSERT INTO `t_sys_menu` VALUES (183, '用户列表', 3, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/user/roleQuery', '', 1001, 1, 'yxkong', '2023-12-28 11:02:25', '', '2023-12-28 11:02:25', '');
INSERT INTO `t_sys_menu` VALUES (184, '新增', 5, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/menu/add', '', 1001, 1, 'yxkong', '2023-12-28 11:03:31', '', '2023-12-28 11:03:31', '');
INSERT INTO `t_sys_menu` VALUES (185, '修改', 5, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/menu/modify', '', 1001, 1, 'yxkong', '2023-12-28 11:04:41', '', '2023-12-28 11:04:41', '');
INSERT INTO `t_sys_menu` VALUES (186, '删除', 5, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/menu/delete', '', 1001, 1, 'yxkong', '2023-12-28 11:05:07', '', '2023-12-28 11:05:07', '');
INSERT INTO `t_sys_menu` VALUES (187, '修改', 7, 3, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/dict/modify', '', 1001, 1, 'yxkong', '2023-12-28 11:11:54', '', '2023-12-28 11:11:54', '');
INSERT INTO `t_sys_menu` VALUES (188, '新增', 8, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/config/add', '', 1001, 1, 'yxkong', '2023-12-28 11:12:34', '', '2023-12-28 11:12:34', '');
INSERT INTO `t_sys_menu` VALUES (189, '修改', 8, 2, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/config/modify', '', 1001, 1, 'yxkong', '2023-12-28 11:12:52', '', '2023-12-28 11:12:52', '');
INSERT INTO `t_sys_menu` VALUES (190, '删除', 8, 3, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/config/delete', '', 1001, 1, 'yxkong', '2023-12-28 11:13:15', '', '2023-12-28 11:13:15', '');
INSERT INTO `t_sys_menu` VALUES (191, '重载配置', 8, 4, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/config/reload', '', 1001, 1, 'yxkong', '2023-12-28 11:13:31', '', '2023-12-28 11:13:31', '');
INSERT INTO `t_sys_menu` VALUES (192, '新增', 11, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/job/add', '', 1001, 1, 'yxkong', '2023-12-28 11:16:34', '', '2023-12-28 11:16:34', '');
INSERT INTO `t_sys_menu` VALUES (193, '修改', 11, 2, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/job/modify', '', 1001, 1, 'yxkong', '2023-12-28 11:16:55', '', '2023-12-28 11:16:55', '');
INSERT INTO `t_sys_menu` VALUES (194, '删除', 11, 3, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/job/delete', '', 1001, 1, 'yxkong', '2023-12-28 11:19:22', '', '2023-12-28 11:19:22', '');
INSERT INTO `t_sys_menu` VALUES (195, '立即执行', 11, 4, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/job/trigger', '', 1001, 1, 'yxkong', '2023-12-28 11:19:45', '', '2023-12-28 11:19:45', '');
INSERT INTO `t_sys_menu` VALUES (196, '暂停任务', 11, 5, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/job/pause', '', 1001, 1, 'yxkong', '2023-12-28 11:20:04', '', '2023-12-28 11:20:04', '');
INSERT INTO `t_sys_menu` VALUES (197, '恢复任务', 11, 6, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/job/resume', '', 1001, 1, 'yxkong', '2023-12-28 11:20:22', '', '2023-12-28 11:20:22', '');
INSERT INTO `t_sys_menu` VALUES (198, '执行日志', 11, 7, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/job/log/query', '', 1001, 1, 'yxkong', '2023-12-28 11:22:58', '', '2023-12-28 11:22:58', '');
INSERT INTO `t_sys_menu` VALUES (199, '任务执行日志', 1, 7, 'jobLog', 'system/job/log/index', NULL, 0, 0, 'M', 0, 0, 'sys/job/log/query', '', 1001, 1, 'yxkong', '2023-12-28 11:23:59', '', '2023-12-28 11:23:59', '');
INSERT INTO `t_sys_menu` VALUES (200, '查询日志', 199, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/job/log/query', '', 1001, 1, 'yxkong', '2023-12-28 11:26:27', '', '2023-12-28 11:26:27', '');
INSERT INTO `t_sys_menu` VALUES (201, '日志详情', 199, 2, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/job/log/detail', '', 1001, 1, 'yxkong', '2023-12-28 11:26:44', '', '2023-12-28 11:26:44', '');
INSERT INTO `t_sys_menu` VALUES (202, '新增', 129, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/gray/add', '', 1001, 1, 'yxkong', '2023-12-28 11:28:42', '', '2023-12-28 11:28:42', '');
INSERT INTO `t_sys_menu` VALUES (203, '修改', 129, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/gray/modify', '', 1001, 1, 'yxkong', '2023-12-28 11:29:03', '', '2023-12-28 11:29:03', '');
INSERT INTO `t_sys_menu` VALUES (204, '删除', 129, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/gray/delete', '', 1001, 1, 'yxkong', '2023-12-28 11:29:26', '', '2023-12-28 11:29:26', '');
INSERT INTO `t_sys_menu` VALUES (205, '登录日志', 1, 8, 'system/loginLog/index', 'loginLog', NULL, 0, 0, 'M', 1, 0, 'sys/login/log/query', 'log', 1001, 1, 'yxkong', '2023-12-28 11:31:18', '', '2023-12-28 11:31:18', '');
INSERT INTO `t_sys_menu` VALUES (206, '同步', 126, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/tool/gen/sync', '', 1001, 1, 'yxkong', '2023-12-28 11:43:35', '', '2023-12-28 11:43:35', '');
INSERT INTO `t_sys_menu` VALUES (207, '配置', 126, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/tool/gen/config', '', 1001, 1, 'yxkong', '2023-12-28 11:43:52', '', '2023-12-28 11:43:52', '');
INSERT INTO `t_sys_menu` VALUES (208, '删除', 126, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/tool/gen/delete', '', 1001, 1, 'yxkong', '2023-12-28 11:44:05', '', '2023-12-28 11:44:05', '');
INSERT INTO `t_sys_menu` VALUES (209, '预览', 126, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/tool/gen/preview', '', 1001, 1, 'yxkong', '2023-12-28 11:44:19', '', '2023-12-28 11:44:19', '');
INSERT INTO `t_sys_menu` VALUES (210, '下载', 126, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/tool/gen/genCode', '', 1001, 1, 'yxkong', '2023-12-28 11:44:34', '', '2023-12-28 11:44:34', '');
INSERT INTO `t_sys_menu` VALUES (211, '保存配置', 130, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/tool/gen/modify', '', 1001, 1, 'yxkong', '2023-12-28 11:47:57', '', '2023-12-28 11:47:57', '');
INSERT INTO `t_sys_menu` VALUES (212, '文件管理', 125, 3, 'fileManage', 'tool/file/index', NULL, 0, 0, 'M', 1, 0, 'sys/tool/file/query', 'pdf', 1001, 1, 'yxkong', '2023-12-28 11:49:13', '', '2023-12-28 11:49:13', '');
INSERT INTO `t_sys_menu` VALUES (213, '新增', 212, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/file/uploud/add', '', 1001, 1, 'yxkong', '2023-12-28 11:55:21', '', '2023-12-28 11:55:21', '');
INSERT INTO `t_sys_menu` VALUES (214, '修改', 212, 2, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/file/uploud/modify', '', 1001, 1, 'yxkong', '2023-12-28 11:56:02', '', '2023-12-28 11:56:02', '');
INSERT INTO `t_sys_menu` VALUES (215, '查看', 212, 3, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/file/uploud/detail\'', '', 1001, 1, 'yxkong', '2023-12-28 11:56:16', '', '2023-12-28 11:56:16', '');
INSERT INTO `t_sys_menu` VALUES (216, '删除', 212, 3, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/file/uploud/delete', '', 1001, 1, 'yxkong', '2023-12-28 11:56:29', '', '2023-12-28 11:56:29', '');
INSERT INTO `t_sys_menu` VALUES (217, '流程管理', 0, 1, 'workflow', NULL, NULL, 0, 0, 'D', 1, 0, NULL, 'example', 1001, 1, 'yxkong', '2023-12-28 11:59:55', '', '2023-12-28 11:59:55', '');
INSERT INTO `t_sys_menu` VALUES (218, '流程定义', 217, 1, 'workflowDefinition', 'workflow/definition/index', NULL, 0, 0, 'M', 1, 0, 'api/workflow/definition/query', 'cascader', 1001, 1, 'yxkong', '2023-12-28 12:01:40', '', '2023-12-28 12:01:40', '');
INSERT INTO `t_sys_menu` VALUES (219, '我的流程', 217, 2, 'workflowInstance', 'workflow/instance/index', NULL, 0, 0, 'M', 1, 0, 'api/workflow/instance/query', 'flow', 1001, 1, 'yxkong', '2023-12-28 12:02:17', '', '2023-12-28 12:02:17', '');
INSERT INTO `t_sys_menu` VALUES (220, '待办任务', 217, 3, 'queryTodo', 'workflow/task/todo', NULL, 0, 0, 'M', 1, 0, 'api/workflow/process/queryTodo', '1-todo', 1001, 1, 'yxkong', '2023-12-28 12:02:48', '', '2023-12-28 12:02:48', '');
INSERT INTO `t_sys_menu` VALUES (221, '表单配置', 217, 4, 'form', 'workflow/task/detail/index', NULL, 0, 0, 'M', 1, 0, 'api/workflow/form/query', 'form', 1001, 1, 'yxkong', '2023-12-28 12:03:28', '', '2023-12-28 12:03:28', '');
INSERT INTO `t_sys_menu` VALUES (222, '条件配置', 217, 5, 'flowcondition', 'workflow/condition/index', NULL, 0, 0, 'M', 1, 0, 'api/workflow/condition/query', 'build', 1001, 1, 'yxkong', '2023-12-28 12:04:13', '', '2023-12-28 12:04:13', '');
INSERT INTO `t_sys_menu` VALUES (223, '新增', 218, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/definition/add', '', 1001, 1, 'yxkong', '2023-12-28 12:11:46', '', '2023-12-28 12:11:46', '');
INSERT INTO `t_sys_menu` VALUES (224, '修改', 218, 2, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/definition/modify', '', 1001, 1, 'yxkong', '2023-12-28 12:12:08', '', '2023-12-28 12:12:08', '');
INSERT INTO `t_sys_menu` VALUES (225, '部署', 218, 3, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/definition/deployById', '', 1001, 1, 'yxkong', '2023-12-28 12:12:26', '', '2023-12-28 12:12:26', '');
INSERT INTO `t_sys_menu` VALUES (226, '停用', 218, 4, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/definition/stopProcessById', '', 1001, 1, 'yxkong', '2023-12-28 12:12:39', '', '2023-12-28 12:12:39', '');
INSERT INTO `t_sys_menu` VALUES (227, '设计', 218, 5, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/definition/design', '', 1001, 1, 'yxkong', '2023-12-28 12:13:00', '', '2023-12-28 12:13:00', '');
INSERT INTO `t_sys_menu` VALUES (228, '删除', 218, 6, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/definition/delete', '', 1001, 1, 'yxkong', '2023-12-28 12:13:26', '', '2023-12-28 12:13:26', '');
INSERT INTO `t_sys_menu` VALUES (229, '历史版本', 218, 7, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/definition/queryHistory', '', 1001, 1, 'yxkong', '2023-12-28 12:13:48', '', '2023-12-28 12:13:48', '');
INSERT INTO `t_sys_menu` VALUES (230, '历史版本中设为最新', 218, 8, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/definition/rest', '', 1001, 1, 'yxkong', '2023-12-28 12:15:11', '', '2023-12-28 12:15:11', '');
INSERT INTO `t_sys_menu` VALUES (231, '查看', 219, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/instance/detail', '', 1001, 1, 'yxkong', '2023-12-28 12:17:03', '', '2023-12-28 12:17:03', '');
INSERT INTO `t_sys_menu` VALUES (232, '删除', 219, 2, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/instance/delete', '', 1001, 1, 'yxkong', '2023-12-28 12:17:17', '', '2023-12-28 12:17:17', '');
INSERT INTO `t_sys_menu` VALUES (233, '待办详情', 217, 3, 'queryTodo/detail', 'workflow/task/detail/index', NULL, 0, 0, 'M', 0, 0, 'api/workflow/process/detail', '', 1001, 1, 'yxkong', '2023-12-28 12:21:13', '', '2023-12-28 12:21:13', '');
INSERT INTO `t_sys_menu` VALUES (234, '审批', 233, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/approval/submit', '', 1001, 1, 'yxkong', '2023-12-28 12:22:56', '', '2023-12-28 12:22:56', '');
INSERT INTO `t_sys_menu` VALUES (235, '办理', 220, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/instance/detail', '', 1001, 1, 'yxkong', '2023-12-28 12:25:11', '', '2023-12-28 12:25:11', '');
INSERT INTO `t_sys_menu` VALUES (236, '新增', 221, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/form/add', '', 1001, 1, 'yxkong', '2023-12-28 12:26:26', '', '2023-12-28 12:26:26', '');
INSERT INTO `t_sys_menu` VALUES (237, '编辑', 221, 2, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/form/modify', '', 1001, 1, 'yxkong', '2023-12-28 12:26:43', '', '2023-12-28 12:26:43', '');
INSERT INTO `t_sys_menu` VALUES (238, '删除', 221, 3, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/form/delete', '', 1001, 1, 'yxkong', '2023-12-28 12:26:57', '', '2023-12-28 12:26:57', '');
INSERT INTO `t_sys_menu` VALUES (239, '新增', 222, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/condition/add', '', 1001, 1, 'yxkong', '2023-12-28 12:27:56', '', '2023-12-28 12:27:56', '');
INSERT INTO `t_sys_menu` VALUES (240, '编辑', 222, 2, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/condition/modify', '', 1001, 1, 'yxkong', '2023-12-28 12:28:18', '', '2023-12-28 12:28:18', '');
INSERT INTO `t_sys_menu` VALUES (241, '查看', 222, 3, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/condition/detail', '', 1001, 1, 'yxkong', '2023-12-28 12:28:35', '', '2023-12-28 12:28:35', '');
INSERT INTO `t_sys_menu` VALUES (242, '删除', 222, 4, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/condition/delete', '', 1001, 1, 'yxkong', '2023-12-28 12:28:55', '', '2023-12-28 12:28:55', '');
INSERT INTO `t_sys_menu` VALUES (243, '项目管理', 0, 1, 'pm', NULL, NULL, 0, 0, 'D', 1, 0, NULL, 'project', 1001, 1, 'yxkong', '2023-12-28 12:29:44', '', '2023-12-28 12:29:44', '');
INSERT INTO `t_sys_menu` VALUES (244, '项目空间', 243, 1, 'domain', 'pm/domain/index', NULL, 0, 0, 'M', 1, 0, 'api/pm/domain/query', 'build', 1001, 1, 'yxkong', '2023-12-28 12:30:25', '', '2023-12-28 12:30:25', '');
INSERT INTO `t_sys_menu` VALUES (245, '项目需求', 243, 1, 'project', 'pm/project/index', NULL, 0, 0, 'M', 1, 0, 'api/pm/project/query', 'chart', 1001, 1, 'yxkong', '2023-12-28 12:31:18', '', '2023-12-28 12:31:18', '');
INSERT INTO `t_sys_menu` VALUES (246, '空间管理', 243, 1, 'domainManage', 'pm/domain/manage', NULL, 0, 0, 'M', 0, 0, 'api/pm/domain/detail', '', 1001, 1, 'yxkong', '2023-12-28 12:32:01', '', '2023-12-28 12:32:01', '');
INSERT INTO `t_sys_menu` VALUES (247, '项目详情', 243, 1, 'projectDetail', 'pm/project/detail/index', NULL, 0, 0, 'M', 0, 0, 'api/pm/project/detail', '', 1001, 1, 'yxkong', '2023-12-28 12:33:30', '', '2023-12-28 12:33:30', '');
INSERT INTO `t_sys_menu` VALUES (248, '默认角色配置', 243, 1, 'domainRole', 'pm/domain/role/index', NULL, 0, 0, 'M', 1, 0, 'api/pm/domain/role/query', 'swagger', 1001, 1, 'yxkong', '2023-12-28 12:36:35', '', '2023-12-28 12:36:35', '');
INSERT INTO `t_sys_menu` VALUES (249, '新增', 244, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/pm/domain/add', '', 1001, 1, 'yxkong', '2023-12-28 12:38:16', '', '2023-12-28 12:38:16', '');
INSERT INTO `t_sys_menu` VALUES (250, '空间管理', 244, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/pm/domain/detail', '', 1001, 1, 'yxkong', '2023-12-28 12:38:35', '', '2023-12-28 12:38:35', '');
INSERT INTO `t_sys_menu` VALUES (251, '状态管理', 244, 3, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/pm/domain/updateStatus', '', 1001, 1, 'yxkong', '2023-12-28 12:39:09', '', '2023-12-28 12:39:09', '');
INSERT INTO `t_sys_menu` VALUES (252, '修改', 246, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/pm/domain/modify', '', 1001, 1, 'yxkong', '2023-12-28 12:40:25', '', '2023-12-28 12:40:25', '');
INSERT INTO `t_sys_menu` VALUES (253, '新增业务线', 246, 2, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/pm/domain/lob/add', '', 1001, 1, 'yxkong', '2023-12-28 12:41:01', '', '2023-12-28 12:41:01', '');
INSERT INTO `t_sys_menu` VALUES (254, '修改业务线', 246, 3, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/pm/domain/lob/modify', '', 1001, 1, 'yxkong', '2023-12-28 12:41:27', '', '2023-12-28 12:41:27', '');
INSERT INTO `t_sys_menu` VALUES (255, '新增空间角色', 246, 4, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/pm/domain/role/add', '', 1001, 1, 'yxkong', '2023-12-28 12:41:51', '', '2023-12-28 12:41:51', '');
INSERT INTO `t_sys_menu` VALUES (256, '修改空间角色', 246, 5, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/pm/domain/role/modify', '', 1001, 1, 'yxkong', '2023-12-28 12:42:16', '', '2023-12-28 12:47:07', '');
INSERT INTO `t_sys_menu` VALUES (257, '删除业务线', 246, 4, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/pm/domain/lob/delete', '', 1001, 1, 'yxkong', '2023-12-28 12:43:31', '', '2023-12-28 12:43:31', '');
INSERT INTO `t_sys_menu` VALUES (258, '删除空间角色', 246, 6, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/pm/domain/role/delete', '', 1001, 1, 'yxkong', '2023-12-28 12:49:23', '', '2023-12-28 12:49:23', '');
INSERT INTO `t_sys_menu` VALUES (259, '保存', 247, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/pm/project/save', '', 1001, 1, 'yxkong', '2023-12-28 12:52:56', '', '2023-12-28 12:52:56', '');
INSERT INTO `t_sys_menu` VALUES (260, '完成节点', 247, 2, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/pm/project/completed', '', 1001, 1, 'yxkong', '2023-12-28 12:53:21', '', '2023-12-28 12:53:21', '');
INSERT INTO `t_sys_menu` VALUES (262, '新增', 245, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/pm/project/add', '', 1001, 1, 'yxkong', '2023-12-28 12:55:40', '', '2023-12-28 12:55:40', '');
INSERT INTO `t_sys_menu` VALUES (263, '查看', 245, 2, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/pm/project/detail', '', 1001, 1, 'yxkong', '2023-12-28 12:56:02', '', '2023-12-28 12:56:02', '');
INSERT INTO `t_sys_menu` VALUES (264, '新增', 248, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/pm/domain/role/add\'', '', 1001, 1, 'yxkong', '2023-12-28 12:56:23', '', '2023-12-28 12:56:23', '');
INSERT INTO `t_sys_menu` VALUES (265, '修改', 248, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/pm/domain/role/modify', '', 1001, 1, 'yxkong', '2023-12-28 12:56:41', '', '2023-12-28 12:56:41', '');
INSERT INTO `t_sys_menu` VALUES (266, '删除', 248, 3, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/pm/domain/role/delete', '', 1001, 1, 'yxkong', '2023-12-28 12:56:56', '', '2023-12-28 12:56:56', '');
INSERT INTO `t_sys_menu` VALUES (267, '短信管理', 0, 2, 'sms', NULL, NULL, 0, 0, 'D', 1, 0, NULL, 'message', 1001, 1, 'yxkong', '2023-12-28 12:57:40', '', '2023-12-28 12:57:40', '');
INSERT INTO `t_sys_menu` VALUES (268, '短信供应商', 267, 1, 'sp', 'sms/sp/index', NULL, 0, 0, 'M', 1, 0, 'sys/sms/sp/query', '', 1001, 1, 'yxkong', '2023-12-28 12:58:14', '', '2023-12-28 12:58:14', '');
INSERT INTO `t_sys_menu` VALUES (269, '短信模板', 267, 1, 'template', 'sms/template/index', NULL, 0, 0, 'M', 1, 0, 'sys/sms/template/query', 'message', 1001, 1, 'yxkong', '2023-12-28 12:59:04', '', '2023-12-28 12:59:04', '');
INSERT INTO `t_sys_menu` VALUES (270, '短信记录', 267, 1, 'smslog', 'sms/log/index', NULL, 0, 0, 'M', 1, 0, 'sys/sms/log/query', 'message', 1001, 1, 'yxkong', '2023-12-28 12:59:42', '', '2023-12-28 12:59:42', '');
INSERT INTO `t_sys_menu` VALUES (271, '短信白名单', 267, 1, 'whitelist', 'sms/whitelist/index', NULL, 0, 0, 'M', 1, 0, 'sys/sms/whitelist/query', 'list', 1001, 1, 'yxkong', '2023-12-28 13:00:20', '', '2023-12-28 13:00:20', '');
INSERT INTO `t_sys_menu` VALUES (272, '短信模板厂商', 267, 1, 'templateStatus', 'sms/template/status', NULL, 0, 0, 'M', 0, 0, 'sys/sms/template/status/query', '', 1001, 1, 'yxkong', '2023-12-28 13:01:04', '', '2023-12-28 13:01:04', '');
INSERT INTO `t_sys_menu` VALUES (273, '新增', 268, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/sms/sp/add', '', 1001, 1, 'yxkong', '2023-12-28 13:12:30', '', '2023-12-28 13:12:30', '');
INSERT INTO `t_sys_menu` VALUES (274, '修改', 268, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/sms/sp/modify', '', 1001, 1, 'yxkong', '2023-12-28 13:12:46', '', '2023-12-28 13:12:46', '');
INSERT INTO `t_sys_menu` VALUES (275, '新增', 269, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/sms/template/add', '', 1001, 1, 'yxkong', '2023-12-28 13:13:24', '', '2023-12-28 13:13:24', '');
INSERT INTO `t_sys_menu` VALUES (276, '编辑', 269, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/sms/template/modify', '', 1001, 1, 'yxkong', '2023-12-28 13:13:54', '', '2023-12-28 13:13:54', '');
INSERT INTO `t_sys_menu` VALUES (277, '开通厂商', 269, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/sms/template/status/query', '', 1001, 1, 'yxkong', '2023-12-28 13:14:15', '', '2023-12-28 13:14:15', '');
INSERT INTO `t_sys_menu` VALUES (278, '新增', 272, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/sms/template/status/add', '', 1001, 1, 'yxkong', '2023-12-28 13:15:14', '', '2023-12-28 13:15:14', '');
INSERT INTO `t_sys_menu` VALUES (279, '修改', 272, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/sms/template/status/modify', '', 1001, 1, 'yxkong', '2023-12-28 13:15:33', '', '2023-12-28 13:15:33', '');
INSERT INTO `t_sys_menu` VALUES (280, '查看', 272, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/sms/template/status/detail', '', 1001, 1, 'yxkong', '2023-12-28 13:15:47', '', '2023-12-28 13:15:47', '');
INSERT INTO `t_sys_menu` VALUES (281, '查看', 270, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/sms/log/detail', '', 1001, 1, 'yxkong', '2023-12-28 13:16:43', '', '2023-12-28 13:16:43', '');
INSERT INTO `t_sys_menu` VALUES (282, '新增', 271, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/sms/whitelist/add', '', 1001, 1, 'yxkong', '2023-12-28 13:17:15', '', '2023-12-28 13:17:15', '');
INSERT INTO `t_sys_menu` VALUES (284, '修改', 271, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/sms/whitelist/modify', '', 1001, 1, 'yxkong', '2023-12-28 13:17:29', '', '2023-12-28 13:17:29', '');
INSERT INTO `t_sys_menu` VALUES (285, '查看', 271, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/sms/whitelist/detail', '', 1001, 1, 'yxkong', '2023-12-28 13:17:51', '', '2023-12-28 13:17:51', '');
INSERT INTO `t_sys_menu` VALUES (286, '删除', 271, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'sys/sms/whitelist/delete', '', 1001, 1, 'yxkong', '2023-12-28 13:17:59', '', '2023-12-28 13:17:59', '');
INSERT INTO `t_sys_menu` VALUES (287, '在线用户', 122, 1, 'online', '	 monitor/online/index', NULL, 0, 0, 'M', 1, 0, 'sys/monitor/online/query', 'online', 1001, 1, 'yxkong', '2023-12-28 13:20:39', '', '2023-12-28 13:20:39', '');
INSERT INTO `t_sys_menu` VALUES (288, '查询项目节点用户', 247, 3, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/pm/project/node/queryNodeUsers', '', 1001, 1, 'yxkong', '2023-12-28 17:26:03', '', '2023-12-28 17:26:03', '');
INSERT INTO `t_sys_menu` VALUES (289, '查询项目节点信息', 247, 3, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/pm/project/node/getNodeInfo', '', 1001, 1, 'yxkong', '2023-12-28 17:26:41', '', '2023-12-28 17:26:41', '');
INSERT INTO `t_sys_menu` VALUES (290, '设置流程用户', 218, 9, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/assignee/users', '', 1001, 1, 'yxkong', '2023-12-28 17:42:17', '', '2023-12-28 17:42:17', '');
INSERT INTO `t_sys_menu` VALUES (291, '查看流程', 218, 1, '', NULL, NULL, 0, 0, 'B', 1, 0, 'api/workflow/definition/detail', '', 1001, 1, 'yxkong', '2023-12-28 18:29:00', '', '2023-12-28 18:29:00', '');
COMMIT;

-- ----------------------------
-- Table structure for t_sys_opt_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_opt_log`;
CREATE TABLE `t_sys_opt_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `trace_id` varchar(64) DEFAULT NULL COMMENT '跟踪ID',
  `module` varchar(30) NOT NULL COMMENT '所属模块',
  `title` varchar(64) DEFAULT NULL COMMENT '日志标题',
  `method` varchar(64) NOT NULL COMMENT '执行方法',
  `url` varchar(128) DEFAULT NULL COMMENT '请求路径',
  `headers` varchar(500) DEFAULT NULL,
  `request_body` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `response_body` varchar(2000) DEFAULT '' COMMENT '返回参数',
  `request_ip` varchar(30) DEFAULT NULL COMMENT 'ip地址',
  `exception` text,
  `execute_time` int(11) DEFAULT NULL COMMENT '执行耗时',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  `create_by` varchar(20) NOT NULL DEFAULT '' COMMENT '创建人(数据归属人)，存储登录名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_url` (`url`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';



-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(30) NOT NULL COMMENT '角色名称',
  `key` varchar(100) DEFAULT NULL COMMENT '角色权限字符串',
  `sort` int(11) DEFAULT NULL COMMENT '显示顺序',
  `data_scope` varchar(30) DEFAULT '1' COMMENT '数据范围（）',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `create_by` varchar(20) DEFAULT '' COMMENT '创建人(数据归属人)，存储登录名',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(20) DEFAULT '' COMMENT '更新人，存储登录名',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色信息表';

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', 1001, 1, 'init', '2023-04-04 16:06:14', 'admin', '2023-04-28 11:40:50', '');
INSERT INTO `t_sys_role` VALUES (2, '租户管理员', 'adminTenant', 2, '1', 1001, 1, 'int', '2023-04-07 18:06:07', 'yxkong', '2023-05-29 11:26:19', '');
INSERT INTO `t_sys_role` VALUES (3, '三方用户默认角色', 'ldapDefault', 2, '1', 1001, 1, 'int', '2023-04-07 18:06:07', 'admin', '2023-06-01 19:21:00', '');
INSERT INTO `t_sys_role` VALUES (4, '普通角色', 'normal', NULL, '3', 1001, 1, 'admin', '2023-04-11 16:19:30', 'admin', '2023-07-26 15:41:51', '');
INSERT INTO `t_sys_role` VALUES (5, '普通测试', 'normaltest', NULL, '2', 1001, 1, 'admin', '2023-04-28 14:16:22', 'admin', '2023-05-08 18:36:19', '');
INSERT INTO `t_sys_role` VALUES (8, '开发测试', 'testdev', NULL, '3', 1001, 1, 'admin', '2023-07-21 15:40:10', 'admin', '2023-07-21 16:21:51', '');
COMMIT;

-- ----------------------------
-- Table structure for t_sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_dept`;
CREATE TABLE `t_sys_role_dept` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`role_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色和部门关联表';

-- ----------------------------
-- Records of t_sys_role_dept
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_menu`;
CREATE TABLE `t_sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色和菜单关联表';


-- ----------------------------
-- Table structure for t_sys_third_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_third_user`;
CREATE TABLE `t_sys_third_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) DEFAULT '0' COMMENT '绑定用户id，系统用户',
  `open_id` varchar(100) NOT NULL DEFAULT '' COMMENT '三方唯一标识，也是三方的用户id',
  `union_id` varchar(50) DEFAULT NULL COMMENT '绑定以后的id，用户在对应的开发中的唯一id',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `user_type` tinyint(4) DEFAULT '1' COMMENT '用户类型,1正式，0外包',
  `channel` varchar(10) NOT NULL DEFAULT 'ldap' COMMENT '用户来源',
  `email` varchar(50) DEFAULT NULL COMMENT '邮件地址',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `join_date` timestamp NULL DEFAULT NULL COMMENT '加入时间',
  `extend1` varchar(30) DEFAULT NULL COMMENT '预留字段1',
  `extend2` varchar(30) DEFAULT NULL COMMENT '预留字段2',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `create_by` varchar(20) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(20) DEFAULT '' COMMENT '更新人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(200) DEFAULT '' COMMENT '备注,扩展细腻',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unq_account_name` (`open_id`,`channel`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='三方用户表';

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dept_id` bigint(20) DEFAULT '-1' COMMENT '部门ID',
  `login_name` varchar(50) NOT NULL DEFAULT '' COMMENT '登录账号',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `mobile` varchar(11) DEFAULT '' COMMENT '手机号码',
  `pwd` varchar(50) DEFAULT '' COMMENT '登录密码',
  `salt` varchar(32) DEFAULT '' COMMENT '密码盐值',
  `email` varchar(64) DEFAULT NULL COMMENT '用户邮箱',
  `channel` varchar(10) DEFAULT NULL COMMENT '来源渠道',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `create_by` varchar(20) NOT NULL DEFAULT '' COMMENT '创建人(数据归属人)，存储登录名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(20) DEFAULT '' COMMENT '更新人，存储登录名',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unq_login_name` (`login_name`) USING BTREE,
  KEY `idx_mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_user` VALUES (1, 1, 'admin', '系统管理员', '13888888888', '27154e69c1279883cd6f8ef7ac912364', 'GLZpXz', NULL, NULL, NULL, 1001, 1, 'admin', '2023-02-11 11:36:34', 'admin', '2023-04-04 16:30:25', '');
INSERT INTO `t_sys_user` VALUES (2, 1, 'yxkong', '鱼翔空', '15201653208', '27154e69c1279883cd6f8ef7ac912364', 'GLZpXz', NULL, NULL, NULL, 1001, 1, 'admin', '2023-01-09 16:59:44', 'yxkong', '2023-04-04 16:30:33', '');
INSERT INTO `t_sys_user` VALUES (4, 4, 'test01', '测试', '13520999990', '', '', NULL, NULL, NULL, 1001, 1, 'admin', '2023-04-11 14:39:34', 'admin', '2023-08-19 21:19:02', '');
INSERT INTO `t_sys_user` VALUES (5, 5, 'test02', '测试2', '15620485729', 'e120441df027f1745da6733f30c4d189', 'gLDYuW', NULL, NULL, NULL, 1001, 0, 'admin', '2023-04-11 16:20:04', 'admin', '2023-09-13 16:03:38', '');
COMMIT;

-- ----------------------------
-- Table structure for t_sys_user_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_log`;
CREATE TABLE `t_sys_user_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `login_name` varchar(30) DEFAULT NULL COMMENT '登录名',
  `biz_type` tinyint(4) DEFAULT '0' COMMENT '业务类型，0，注册，1,登录，2，改密，3注销，4，绑定账户',
  `request_ip` varchar(16) DEFAULT NULL COMMENT '注册ip',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户日志表';



-- ----------------------------
-- Table structure for t_sys_user_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_menu`;
CREATE TABLE `t_sys_user_menu` (
  `user_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`user_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户和菜单关联表';

-- ----------------------------
-- Records of t_sys_user_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户和角色关联表';

-- ----------------------------
INSERT INTO `t_sys_user_role` (`user_id`, `role_id`, `tenant_id`) VALUES (1, 1, 1001);
INSERT INTO `t_sys_user_role` (`user_id`, `role_id`, `tenant_id`) VALUES (2, 1, 1001);
SET FOREIGN_KEY_CHECKS = 1;

```