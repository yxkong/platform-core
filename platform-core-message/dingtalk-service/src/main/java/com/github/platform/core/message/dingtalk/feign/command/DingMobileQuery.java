package com.github.platform.core.message.dingtalk.feign.command;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 手机号
 * @author: yxkong
 * @date: 2024/4/25 21:31
 * @version: 1.0
 */
@Data
@AllArgsConstructor
public class DingMobileQuery {
    private String mobile;
}
