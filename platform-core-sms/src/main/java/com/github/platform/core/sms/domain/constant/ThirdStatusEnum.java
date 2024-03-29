package com.github.platform.core.sms.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 三方状态枚举
 * @author: yxkong
 * @date: 2024/3/27 13:52
 * @version: 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ThirdStatusEnum {
    NO_REQUEST(0,"未申请"),
    SUC(1,"申请成功"),
    REQUEST(2,"申请中"),
    ERROR(-1,"申请失败");


    private Integer status;
    private String desc;
}
