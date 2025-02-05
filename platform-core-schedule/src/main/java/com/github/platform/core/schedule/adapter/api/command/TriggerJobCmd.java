package com.github.platform.core.schedule.adapter.api.command;

import com.github.platform.core.common.entity.StrIdReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 触发
 * @Author: yxkong
 * @Date: 2025/2/2
 * @version: 1.0
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TriggerJobCmd extends StrIdReq {
    private String handlerParam;
}
