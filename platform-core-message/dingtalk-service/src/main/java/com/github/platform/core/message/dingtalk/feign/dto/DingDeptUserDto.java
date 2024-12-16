package com.github.platform.core.message.dingtalk.feign.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * 钉钉部门用户
 * @author
 */
@Data
public class DingDeptUserDto {
	/**部门用户*/
    private List<DingUserDto> list;

    /**如果has_more为false，表示没有更多的分页数据*/
    @JsonProperty("next_cursor")
    private Integer nextCursor;

    /**是否还有更多的数据：true：有 false：没有*/
    @JsonProperty("has_more")
    private boolean hasMore;

}
