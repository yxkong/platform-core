package com.github.platform.core.dingtalk.adapter.api.command;

import com.github.platform.core.sys.adapter.api.command.TenantBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 钉钉授权
 * @author: yxkong
 * @date: 2024/4/26 18:43
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DingAuthCmd extends TenantBase {
    /**钉钉返回的authCode*/
    private String authCode;
    /**钉钉返回的state*/
    private String state;
}
