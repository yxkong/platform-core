```sql
-- ----------------------------
-- Table structure for t_gray_rule
-- ----------------------------
DROP TABLE IF EXISTS `t_gray_rule`;
CREATE TABLE `t_gray_rule` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`rule` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '规则',
`name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '规则描述',
`label` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '标签',
`version` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '版本',
`tenant` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '租户',
`status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0停用）',
`create_by` varchar(20) COLLATE utf8_bin DEFAULT '' COMMENT '创建人(数据归属人)，存储登录名',
`create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_by` varchar(20) COLLATE utf8_bin DEFAULT '' COMMENT '更新人，存储登录名',
`update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
`remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='灰度规则表';

-- ----------------------------
-- Records of t_gray_rule
-- ----------------------------
BEGIN;
INSERT INTO `t_gray_rule` VALUES (1, '(string.startsWith(\'9,0\',mobileMod) && userId>=1 && registerTime>\'2022-06-01 00:00:00\')', '测试规则1', 'gray', NULL, '1001', 1, 'yxkong', '2023-04-19 14:34:26', '', '2023-04-19 14:42:27', '尾号为9,0,且userId>901,注册时间大于2021年6月25日');
COMMIT;
```