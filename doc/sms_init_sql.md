```sql

-- ----------------------------
-- Table structure for t_sys_sms_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_sms_log`;
CREATE TABLE `t_sys_sms_log` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`pro_no` varchar(32) DEFAULT NULL COMMENT '厂商编号，用于统计花费',
`mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
`msg_id` varchar(100) NOT NULL COMMENT '消息唯一流水号',
`third_msg_id` varchar(32) DEFAULT NULL COMMENT '三方消息id',
`temp_no` varchar(32) NOT NULL COMMENT '模板编号',
`content` varchar(200) NOT NULL COMMENT '短信内容',
`status` tinyint(4) NOT NULL DEFAULT '-2' COMMENT '任务状态，-2任务异常,-1 短信发送失败,；0，未发送，1，短信发送成功，2已发送',
`tenant` varchar(10) DEFAULT NULL COMMENT '租户',
`send_status` tinyint(4) DEFAULT '0' COMMENT '回调回来的状态，0默认，-1发送失败，1发送成功',
`create_time` timestamp NOT NULL COMMENT '创建时间',
`create_by` varchar(20) DEFAULT NULL COMMENT '创建人（保存登录名称）',
`update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '外部回调会更新',
`remark` varchar(200) DEFAULT NULL COMMENT '备注，主要是服务商失败的原因',
PRIMARY KEY (`id`),
UNIQUE KEY `uniq_mobile_third_msg_id` (`mobile`,`third_msg_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='短信发送记录表';



-- ----------------------------
-- Table structure for t_sys_sms_service_provider
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_sms_service_provider`;
CREATE TABLE `t_sys_sms_service_provider` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`pro_no` varchar(32) NOT NULL COMMENT '厂商编号',
`pro_alias` varchar(20) NOT NULL COMMENT '厂商简称',
`pro_name` varchar(50) NOT NULL COMMENT '厂商名称',
`bean_name` varchar(32) DEFAULT NULL COMMENT '厂商服务的bean名称',
`interface_url` varchar(200) DEFAULT NULL COMMENT '厂商接口url',
`account` varchar(32) NOT NULL COMMENT '厂商分配的账户',
`encrypt_pwd` varchar(100) NOT NULL COMMENT '加密密码',
`salt` varchar(20) NOT NULL COMMENT '加密盐值',
`tenant` varchar(10) DEFAULT NULL COMMENT '租户',
`status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0停用）',
`usable_num` int(11) DEFAULT NULL COMMENT '可用条数',
`alarm_threshold` int(11) DEFAULT NULL COMMENT '报警阈值',
`sort` int(11) DEFAULT NULL COMMENT '排序',
`create_time` timestamp NOT NULL COMMENT '创建时间',
`create_by` varchar(20) NOT NULL COMMENT '创建人（保存登录名称）',
`update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
`update_by` varchar(20) DEFAULT NULL COMMENT '更新人（保存登录名称）',
`remark` varchar(200) DEFAULT NULL COMMENT '备注',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='短信服务商表';


-- ----------------------------
-- Table structure for t_sys_sms_template
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_sms_template`;
CREATE TABLE `t_sys_sms_template` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`temp_no` varchar(32) NOT NULL COMMENT '模板编号',
`sms_type` varchar(10) NOT NULL COMMENT '短信类型（业务类型）',
`name` varchar(50) NOT NULL COMMENT '模板名称',
`sign_name` varchar(20) NOT NULL COMMENT '短信签名',
`content` varchar(1000) NOT NULL COMMENT '模板内容',
`type` varchar(10) DEFAULT NULL COMMENT '短信类型：verify/notice/market',
`use_time_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '可用时间类型：0，无限制，1自定义',
`work_interval` varchar(20) DEFAULT NULL COMMENT '工作时间区间 00:00:00 - 23:59:59',
`holiday_interval` varchar(20) DEFAULT NULL COMMENT '节假日时间区间 00:00:00 - 23:59:59',
`apply_name` varchar(50) DEFAULT NULL COMMENT '申请模板的公司名称',
`apply_url` varchar(100) DEFAULT NULL COMMENT '申请用的公司网址',
`apply_reason` varchar(200) DEFAULT NULL COMMENT '申请原因说明',
`tenant` varchar(10) DEFAULT NULL COMMENT '租户',
`status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0停用）',
`create_time` timestamp NOT NULL COMMENT '创建时间',
`create_by` varchar(20) NOT NULL COMMENT '创建人（保存登录名称）',
`update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
`update_by` varchar(20) DEFAULT NULL COMMENT '更新人（保存登录名称）',
`remark` varchar(200) DEFAULT NULL COMMENT '备注',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='短信模板表';



-- ----------------------------
-- Table structure for t_sys_sms_template_status
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_sms_template_status`;
CREATE TABLE `t_sys_sms_template_status` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`temp_no` varchar(32) NOT NULL COMMENT '模板编号',
`pro_no` varchar(32) NOT NULL COMMENT '厂商编号',
`sign_status` tinyint(4) NOT NULL DEFAULT '2' COMMENT '签名状态：0，未申请，1申请成功，2，申请中  -1，申请失败',
`sign_msg_id` varchar(50) DEFAULT NULL COMMENT '签名申请三方消息id，用于回调以后查找数据',
`temp_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '模板状态：0，未申请，1申请成功，2申请中  -1，申请失败',
`temp_id` varchar(20) DEFAULT NULL COMMENT '服务商的模板id',
`temp_msg_id` varchar(50) DEFAULT NULL COMMENT '模板申请三方消息id',
`tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
`sort` int(11) DEFAULT NULL COMMENT '排序',
`status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0停用）',
`type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '添加类型：0，程序，1人工',
`create_time` timestamp NOT NULL COMMENT '创建时间',
`create_by` varchar(20) NOT NULL COMMENT '创建人（保存登录名称）',
`update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
`update_by` varchar(20) DEFAULT NULL COMMENT '更新人（保存登录名称）',
`remark` varchar(200) DEFAULT NULL COMMENT '备注',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='短信模板关联厂商对应的签名，模板状态等';


-- ----------------------------
-- Table structure for t_sys_sms_white_list
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_sms_white_list`;
CREATE TABLE `t_sys_sms_white_list` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`channel` varchar(20) DEFAULT NULL COMMENT '渠道',
`name` varchar(20) DEFAULT NULL COMMENT '姓名',
`mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
`status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态1，启用，0禁用',
`tenant` varchar(10) DEFAULT NULL COMMENT '租户',
`create_time` timestamp NOT NULL COMMENT '创建时间',
`create_by` varchar(20) DEFAULT NULL COMMENT '创建人',
`update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
`update_by` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '更新人',
`remark` varchar(200) DEFAULT NULL COMMENT '备注',
PRIMARY KEY (`id`),
UNIQUE KEY `uniq_mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='短信发送白名单';


```