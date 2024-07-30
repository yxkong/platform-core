```sql
CREATE TABLE `t_sys_upload_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `module` varchar(20) DEFAULT NULL COMMENT '模块名称',
  `biz_no` varchar(32) DEFAULT NULL COMMENT '业务号，业务方关联',
  `file_id` varchar(128) DEFAULT NULL COMMENT '文件唯一标识',
  `file_name` varchar(64) NOT NULL COMMENT '文件名',
  `storage` varchar(10) DEFAULT NULL COMMENT '存储方式，disk，ctyun（天翼云）,aliyun,',
  `tenant` varchar(10) DEFAULT NULL COMMENT '租户',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `create_time` timestamp NOT NULL COMMENT '创建时间',
  `create_by` varchar(20) DEFAULT NULL COMMENT '创建人',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unq_file_id` (`file_id`),
  KEY `idx_module_biz` (`module`,`biz_no`),
  KEY `biz_no` (`biz_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='上传文件表';
```