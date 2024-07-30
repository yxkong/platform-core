```sql
DROP TABLE IF EXISTS `t_ding_dept`;
CREATE TABLE `t_ding_dept` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`dept_id` bigint(20) DEFAULT NULL COMMENT '部门id',
`name` varchar(30) NOT NULL COMMENT '部门名称',
`parent_id` bigint(20) DEFAULT NULL COMMENT '父部门id',
`tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
`status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0停用）',
`create_by` varchar(20) NOT NULL DEFAULT '' COMMENT '创建人',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_by` varchar(20) DEFAULT '' COMMENT '更新人',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
`remark` varchar(200) DEFAULT '' COMMENT '备注',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='钉钉部门表';
```