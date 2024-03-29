package com.github.platform.core.dingtalk.infra.rpc.external.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取token请求体
 * @author: yxkong
 * @date: 2024/1/19 13:33
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DingAccessTokenCmd {
    private String appKey;
    private String appSecret;

}
