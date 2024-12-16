package com.github.platform.core.message.dingtalk.feign.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 钉钉用户查询
 * @author: yxkong
 * @date: 2024/1/19 14:00
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DingUserQuery {
    @JsonProperty("userid")
    private String userId;
    /**通讯录语言*/
    @Builder.Default
    private String language = "zh_CN";
}
