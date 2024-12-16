package com.github.platform.core.message.dingtalk.feign.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 钉钉群成员查询
 * @author: yxkong
 * @date: 2024/1/19 15:50
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DingGroupUserQuery {
    /**
     * 基于群模板创建的群
     */
    private String openConversationId;
    /**（非必填）群聊酷应用编码*/
    private String coolAppCode;
    /**分页大小,如果群成员数量不超过1000，而直接一次性返回全部群成员*/
    private Long maxResults;
    /**分页游标，置空表示从首页开始查询*/
    private String nextToken;
}
