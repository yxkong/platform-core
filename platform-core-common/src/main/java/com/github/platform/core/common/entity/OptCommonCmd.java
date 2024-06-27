package com.github.platform.core.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 操作通用命令
 *
 * @author: yxkong
 * @date: 2024/6/8 10:25
 * @version: 1.0
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OptCommonCmd extends StrIdReq{
    /**原因*/
    protected String reason;
}
