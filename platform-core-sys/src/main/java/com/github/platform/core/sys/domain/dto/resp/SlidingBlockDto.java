package com.github.platform.core.sys.domain.dto.resp;

import lombok.Builder;
import lombok.Getter;

/**
 * 滑块验证实体
 * @author: yxkong
 * @date: 2022/11/15 11:21 AM
 * @version: 1.0
 */
@Getter
@Builder
public class SlidingBlockDto {
    /**
     * 验证结果，true通过，false 未通过
     */
    private Boolean result;
    /**
     * 失败次数
     */
    private String failTimes;
    /**
     * 失败信息
     */
    private String  message;
}
