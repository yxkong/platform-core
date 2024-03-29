package com.github.platform.core.monitor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 在线用户
 * @author: yxkong
 * @date: 2023/6/8 5:30 PM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnLineUserDto {

    private String loginName;
    private String userName;
    private String deptName;
    private String requestIp;
    private String os;
    private String browser;
    private String loginType;
    private String loginTime;
    /**最后一次时间*/
    private String lastTime;
    private String status;

}
