package com.github.platform.core.message.dingtalk.feign.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取企业应用token请求体
 * @author: yxkong
 * @date: 2024/1/19 13:33
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DingAppAccessTokenCmd {
    /**已创建的企业内部应用的AppKey*/
    private String appKey;
    /**已创建的企业内部应用的AppSecret*/
    private String appSecret;

}
