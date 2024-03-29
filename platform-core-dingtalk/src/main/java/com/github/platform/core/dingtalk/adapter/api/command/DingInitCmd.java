package com.github.platform.core.dingtalk.adapter.api.command;

import com.github.platform.core.sys.adapter.api.command.TenantBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 钉钉初始化
 * @author: yxkong
 * @date: 2024/3/26 11:05
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DingInitCmd extends TenantBase {

    private Long deptId;
}
