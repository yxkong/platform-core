package com.github.platform.core.message.dingtalk.feign.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 群成员
 * @author: yxkong
 * @date: 2024/1/19 15:56
 * @version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class DingGroupUserDto extends DingResultBean {

    /**群成员id*/
    private List<String> memberUserIds;
    /**是否还有更多数据*/
    private boolean hasMore;
    /**下一次请求的游标，若没有更多数据，则此参数为空*/
    private String nextToken;

}
